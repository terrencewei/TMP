package Endeca.ContentItem;

public class FormatContentItem {
    // private static final char BACKSPACE = ' ';
    private static final char BACKSPACE  = '\t';
    private static final char CHANGELINE = '\n';
    // private static final char CHANGELINE = '\r';



    public static void main(String[] args) {
        String jsonStr = "{multiSelect=false, @type=searchRefinementHierarchy, name=Categories Hierarchy Refinement, endeca:auditInfo={ecr:resourcePath=content/Shared/searchSlot/searchSlot, ecr:innerPath=leftSectionSearchs[0]}, ancestors=[], rootNodes=[com.aaxiscommerce.acp.acrs.endeca.handler.ACRSHierarchyRefinementHandler$HierarchyRefinement@4b4a6277, com.aaxiscommerce.acp.acrs.endeca.handler.ACRSHierarchyRefinementHandler$HierarchyRefinement@86993f, com.aaxiscommerce.acp.acrs.endeca.handler.ACRSHierarchyRefinementHandler$HierarchyRefinement@4b6f75e], displayName=Categories, dimenionDisplayLabel=, dimensionName=Categories, whyPrecedenceRuleFired=null} ";
        String fotmatStr = FormatContentItem.format(jsonStr);
        System.out.println(fotmatStr);
    }



    public static String format(String jsonStr) {
        int level = 0;
        StringBuffer jsonForMatStr = new StringBuffer();
        for (int i = 0; i < jsonStr.length(); i++) {
            char c = jsonStr.charAt(i);
            if (level > 0 && CHANGELINE == jsonForMatStr.charAt(jsonForMatStr.length() - 1)) {
                jsonForMatStr.append(getLevelStr(level));
            }
            switch (c) {
            case '{':
                // case '[':
                jsonForMatStr.append(c + (CHANGELINE + ""));
                level++;
                break;
            case ',':
                jsonForMatStr.append(c + (CHANGELINE + ""));
                break;
            case '}':
                // case ']':
                jsonForMatStr.append((CHANGELINE + ""));
                level--;
                jsonForMatStr.append(getLevelStr(level));
                jsonForMatStr.append(c);
                break;
            default:
                jsonForMatStr.append(c);
                break;
            }
        }
        return jsonForMatStr.toString();
    }



    private static String getLevelStr(int level) {
        StringBuffer levelStr = new StringBuffer();
        for (int levelI = 0; levelI < level; levelI++) {
            levelStr.append(BACKSPACE + "");
        }
        return levelStr.toString();
    }
}
