package Util;

import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * rule:
 * 
 * 1. current left/right padding must always displayed
 * 
 * 2. min/max page must always displayed
 * 
 * 3. the total count of displayed page numbers must not less than
 * pMinExistCount
 * 
 * 4. if dots only presents 1 number, then do not display the dots, display the
 * number instead
 * 
 * 
 * 
 * output:
 * 
 * 
 * 
 * totalPage is: 1
 * 
 * current page is: 1, displayed pages will be: [1]
 ***********
 * 
 * 
 * totalPage is: 2
 * 
 * current page is: 1, displayed pages will be: [1] 2
 * 
 * current page is: 2, displayed pages will be: 1 [2]
 ***********
 * 
 * 
 * totalPage is: 3
 * 
 * current page is: 1, displayed pages will be: [1] 2 3
 * 
 * current page is: 2, displayed pages will be: 1 [2] 3
 * 
 * current page is: 3, displayed pages will be: 1 2 [3]
 ***********
 * 
 * 
 * totalPage is: 4
 * 
 * current page is: 1, displayed pages will be: [1] 2 3 4
 * 
 * current page is: 2, displayed pages will be: 1 [2] 3 4
 * 
 * current page is: 3, displayed pages will be: 1 2 [3] 4
 * 
 * current page is: 4, displayed pages will be: 1 2 3 [4]
 ***********
 * 
 * 
 * totalPage is: 5
 * 
 * current page is: 1, displayed pages will be: [1] 2 3 4 5
 * 
 * current page is: 2, displayed pages will be: 1 [2] 3 4 5
 * 
 * current page is: 3, displayed pages will be: 1 2 [3] 4 5
 * 
 * current page is: 4, displayed pages will be: 1 2 3 [4] 5
 * 
 * current page is: 5, displayed pages will be: 1 2 3 4 [5]
 ***********
 * 
 * 
 * totalPage is: 6
 * 
 * current page is: 1, displayed pages will be: [1] 2 3 ... 6
 * 
 * current page is: 2, displayed pages will be: 1 [2] 3 ... 6
 * 
 * current page is: 3, displayed pages will be: 1 2 [3] 4 5 6
 * 
 * current page is: 4, displayed pages will be: 1 2 3 [4] 5 6
 * 
 * current page is: 5, displayed pages will be: 1 ... 4 [5] 6
 * 
 * current page is: 6, displayed pages will be: 1 ... 4 5 [6]
 ***********
 * 
 * 
 * totalPage is: 7
 * 
 * current page is: 1, displayed pages will be: [1] 2 3 ... 7
 * 
 * current page is: 2, displayed pages will be: 1 [2] 3 ... 7
 * 
 * current page is: 3, displayed pages will be: 1 2 [3] 4 ... 7
 * 
 * current page is: 4, displayed pages will be: 1 2 3 [4] 5 6 7
 * 
 * current page is: 5, displayed pages will be: 1 ... 4 [5] 6 7
 * 
 * current page is: 6, displayed pages will be: 1 ... 5 [6] 7
 * 
 * current page is: 7, displayed pages will be: 1 ... 5 6 [7]
 ***********
 * 
 * 
 * totalPage is: 8
 * 
 * current page is: 1, displayed pages will be: [1] 2 3 ... 8
 * 
 * current page is: 2, displayed pages will be: 1 [2] 3 ... 8
 * 
 * current page is: 3, displayed pages will be: 1 2 [3] 4 ... 8
 * 
 * current page is: 4, displayed pages will be: 1 2 3 [4] 5 ... 8
 * 
 * current page is: 5, displayed pages will be: 1 ... 4 [5] 6 7 8
 * 
 * current page is: 6, displayed pages will be: 1 ... 5 [6] 7 8
 * 
 * current page is: 7, displayed pages will be: 1 ... 6 [7] 8
 * 
 * current page is: 8, displayed pages will be: 1 ... 6 7 [8]
 ***********
 * 
 * 
 * totalPage is: 9
 * 
 * current page is: 1, displayed pages will be: [1] 2 3 ... 9
 * 
 * current page is: 2, displayed pages will be: 1 [2] 3 ... 9
 * 
 * current page is: 3, displayed pages will be: 1 2 [3] 4 ... 9
 * 
 * current page is: 4, displayed pages will be: 1 2 3 [4] 5 ... 9
 * 
 * current page is: 5, displayed pages will be: 1 ... 4 [5] 6 ... 9
 * 
 * current page is: 6, displayed pages will be: 1 ... 5 [6] 7 8 9
 * 
 * current page is: 7, displayed pages will be: 1 ... 6 [7] 8 9
 * 
 * current page is: 8, displayed pages will be: 1 ... 7 [8] 9
 * 
 * current page is: 9, displayed pages will be: 1 ... 7 8 [9]
 ***********
 * 
 * 
 * totalPage is: 10
 * 
 * current page is: 1, displayed pages will be: [1] 2 3 ... 10
 * 
 * current page is: 2, displayed pages will be: 1 [2] 3 ... 10
 * 
 * current page is: 3, displayed pages will be: 1 2 [3] 4 ... 10
 * 
 * current page is: 4, displayed pages will be: 1 2 3 [4] 5 ... 10
 * 
 * current page is: 5, displayed pages will be: 1 ... 4 [5] 6 ... 10
 * 
 * current page is: 6, displayed pages will be: 1 ... 5 [6] 7 ... 10
 * 
 * current page is: 7, displayed pages will be: 1 ... 6 [7] 8 9 10
 * 
 * current page is: 8, displayed pages will be: 1 ... 7 [8] 9 10
 * 
 * current page is: 9, displayed pages will be: 1 ... 8 [9] 10
 * 
 * current page is: 10, displayed pages will be: 1 ... 8 9 [10]
 ***********
 * 
 * 
 * 
 * 
 * test pagination output using:
 * 
 * currentPageDisplayPadding: 1
 * 
 * minExistPageNumberCount: 4
 *
 * Created by terrencewei on 2017/09/21.
 */
public class PaginationCalc {

    private static final int PADDING         = 1;
    private static final int MIN_EXIST_COUNT = 4;



    public static void main(String[] args) {
        for (int i = 1; i <= 10; i++) {
            System.out.println("totalPage is: " + i);
            printPagination(i);
            System.out.println("***********");
        }
        System.out.println(new StringBuilder().append("\n").append("test pagination output using:").append("\n")
                .append("currentPageDisplayPadding: ").append(getCurrentPageDisplayPadding()).append("\n")
                .append("minExistPageNumberCount: ").append(getMinExistPageNumberCount()).append("\n"));
    }



    /**
     * caculate the display pages based on the rule:
     *
     * 1. current left/right padding must always displayed
     *
     * 2. min/max page must always displayed
     *
     * 3. the total count of displayed page numbers must not less than
     * pMinExistCount
     *
     * 4. if dots only presents 1 number, then do not display the dots, display the
     * number instead
     *
     * For example, if the min=1, max=7, padding=1, minExistCount=4
     *
     * current is: 1, displayed pages will be: [1] 2 3 ... 7
     *
     * current is: 2, displayed pages will be: 1 [2] 3 ... 7
     *
     * current is: 3, displayed pages will be: 1 2 [3] 4 ... 7
     *
     * current is: 4, displayed pages will be: 1 2 3 [4] 5 6 7
     *
     * current is: 5, displayed pages will be: 1 ... 4 [5] 6 7
     *
     * current is: 6, displayed pages will be: 1 ... 5 [6] 7
     *
     * current is: 7, displayed pages will be: 1 ... 5 6 [7]
     *
     * @param pMin
     *            the min displayed page number
     * @param pMax
     *            the max displayed page number
     * @param pCurrent
     *            the current displayed page number
     * @param pPadding
     *            the padding of current displayd page number
     * @param pMinExistCount
     *            the min total count of displayed page numbers
     * @param pDots
     *            the dots symbol
     * @return the dsiplayed pages list contains dots symbol
     */
    protected static List<Integer> calcDisplayPages(int pMin, int pMax, int pCurrent, int pPadding, int pMinExistCount,
            int pDots) {
        int minWidth = pCurrent - pPadding;
        int maxWidth = pCurrent + pPadding;
        Set<Integer> pages = new TreeSet<Integer>();
        // add min/max page
        // min and max page always display
        pages.add(pMin);
        pages.add(pMax);
        // add padding page
        for (int i = minWidth; i <= maxWidth; i++) {
            if (pMin <= i && i <= pMax) {
                pages.add(i);
            }
        }
        // complete min exist count
        if (pages.size() < pMinExistCount && pMinExistCount < pMax) {
            // if the page display total count: minPage + maxPage + (currentPage +
            // left/right padding pages) is less than minExistCount, its data issue, cannot
            // complete the missing page display count
            if (1 + 1 + (1 + pPadding * 2) < pMinExistCount) {
                vlogWarning("wrong minExistCount:{0} (padding:{1})", new Object[] { pMinExistCount, pPadding });
            } else {
                // start complete
                int missing = pMinExistCount - pages.size();
                if (minWidth <= pMin) {
                    // missing by left padding
                    // case: 1 2 3 ... max
                    for (int i = 1; i <= missing; i++) {
                        pages.add(maxWidth + i);
                    }
                } else if (maxWidth >= pMax) {
                    // missing by right padding
                    // case: 1 ... X X max
                    for (int i = 1; i <= missing; i++) {
                        pages.add(minWidth - i);
                    }
                }
            }
        }

        // complete dots(the "...") between missing pages
        Object[] pagesArr = pages.toArray();
        List<Integer> displayPages = new LinkedList<Integer>();
        for (int i = 0; i < pagesArr.length; i++) {
            int now = ((Integer) pagesArr[i]).intValue();
            if (i > 0) {
                int pre = ((Integer) pagesArr[i - 1]).intValue();
                if (pre != pDots && (pre + 1 != now)) {
                    if (pre + 2 != now) {
                        // add dots
                        displayPages.add(pDots);
                    } else {
                        // if dots only presents 1 number, do not display dots, display number instead
                        displayPages.add(pre + 1);
                    }
                }
            }
            displayPages.add(now);
        }
        return displayPages;
    }



    private static void printPagination(int pMax) {
        int min = 1;
        int max = pMax;
        int padding = getCurrentPageDisplayPadding();
        int minExistCount = getMinExistPageNumberCount();
        int dots = -1;
        StringBuilder output = new StringBuilder();
        for (int i = min; i <= max; i++) {
            int current = i;
            output.append("current page is: ").append(current).append(", displayed pages will be: ");
            List<Integer> displayPages = calcDisplayPages(min, max, i, padding, minExistCount, dots);
            for (Integer out : displayPages) {
                if (out == dots) {
                    output.append("..." + " ");
                } else if (out == current) {
                    output.append("[" + out + "] ");
                } else {
                    output.append(" " + out + "  ");
                }
            }
            output.append("\n");
        }
        System.out.print(output.toString());
    }



    private static void vlogWarning(String pS, Object[] pObjects) {
        System.out.println(MessageFormat.format(pS, pObjects));
    }



    public static int getCurrentPageDisplayPadding() {
        return PADDING;
    }



    public static int getMinExistPageNumberCount() {
        return MIN_EXIST_COUNT;
    }
}
