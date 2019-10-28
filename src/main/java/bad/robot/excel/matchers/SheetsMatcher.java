/*
 * Copyright (c) 2012-2013, bad robot (london) ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package bad.robot.excel.matchers;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import static bad.robot.excel.matchers.MergedRegionsMatcher.hasSameMergedRegions;
import static bad.robot.excel.matchers.RowNumberMatcher.hasSameNumberOfRowAs;
import static bad.robot.excel.matchers.RowsMatcher.hasSameRowsAs;
import static bad.robot.excel.sheet.SheetIterable.sheetsOf;

public class SheetsMatcher extends TypeSafeDiagnosingMatcher<Workbook> {

    private final Workbook expected;

    public SheetsMatcher(Workbook expected) {
        this.expected = expected;
    }

    @Override
    protected boolean matchesSafely(Workbook actual, Description mismatch) {
        for (Sheet expectedSheet : sheetsOf(expected)) {
            Sheet actualSheet = actual.getSheet(expectedSheet.getSheetName());

            if (!hasSameMergedRegions(expectedSheet).matchesSafely(actualSheet, mismatch))
                return false;
            
            if (!hasSameNumberOfRowAs(expectedSheet).matchesSafely(actualSheet, mismatch))
                return false;

            if (!hasSameRowsAs(expectedSheet).matchesSafely(actualSheet, mismatch))
                return false;
        }
        return true;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("equality on all sheets in workbook");
    }
}
