package Endeca.Controller;

public class Test2 {

    public static void main(String[] args) {
        usage();
    }


    private static void usage()
    /*      */   {
    /* 1079 */     StringBuilder sb = new StringBuilder(4096);
    /* 1080 */     sb.append("USAGE:\n");
    /* 1081 */     sb.append("java Controller [options] --app-config <app config> <object> <method> [args]\n");
    /* 1082 */     sb.append("\n");
    /* 1083 */     sb.append("The controller will invoke a method on an object defined in the app\n");
    /* 1084 */     sb.append("configuration document(s). Method return values are not captured and\n");
    /* 1085 */     sb.append("only String arguments may be passed as method parameters. The controller\n");
    /* 1086 */     sb.append("will typically be used to run scripts, components or utilities. More\n");
    /* 1087 */     sb.append("complex invocations should usually be wrapped in a BeanShell script.\n");
    /* 1088 */     sb.append("\n");
    /* 1089 */     sb.append("By default, the controller will compare provisioning in the app config\n");
    /* 1090 */     sb.append("document(s) to the provisioning in the EAC. If any definition changes are\n");
    /* 1091 */     sb.append("found, elements are re-provisioned.\n");
    /* 1092 */     sb.append("\n");
    /* 1093 */     sb.append("  Available options:\n");
    /* 1094 */     sb.append("    --help\n");
    /* 1095 */     sb.append("        Displays this usage information. If app config\n");
    /* 1096 */     sb.append("        document and object name specified, available \n");
    /* 1097 */     sb.append("        methods will be displayed.\n");
    /* 1098 */     sb.append("    --version\n");
    /* 1099 */     sb.append("        Displays version information.\n");
    /* 1100 */     sb.append("    --update-definition\n");
    /* 1101 */     sb.append("        Updates application provisioning without invoking\n");
    /* 1102 */     sb.append("        any action. Any specified object, method and args\n");
    /* 1103 */     sb.append("        will be ignored.\n");
    /* 1104 */     sb.append("    --skip-definition\n");
    /* 1105 */     sb.append("        Skips the default provisioning check, invoking the\n");
    /* 1106 */     sb.append("        requested action with the app definition currently\n");
    /* 1107 */     sb.append("        provisioned in the EAC.\n");
    /* 1108 */     sb.append("    --remove-app\n");
    /* 1109 */     sb.append("        Removes the application from the EAC.\n");
    /* 1110 */     sb.append("        WARNING: Any active components will be stopped.\n");
    /* 1111 */     sb.append("    --print-status\n");
    /* 1112 */     sb.append("        Displays the status of application components.\n");
    /* 1113 */     sb.append("    --config-override <override props file>\n");
    /* 1114 */     sb.append("        Name of an app configuration override properties\n");
    /* 1115 */     sb.append("        file to read from the classpath. Multiple override\n");
    /* 1116 */     sb.append("        files may be specified.\n");
    /* 1117 */     sb.append("\n");
    /* 1118 */     sb.append("  <app config>\n");
    /* 1119 */     sb.append("      Name of an app configuration document to read\n");
    /* 1120 */     sb.append("      from the classpath. Multiple documents may be\n");
    /* 1121 */     sb.append("      specified.\n");
    /* 1122 */     sb.append("  <object>\n");
    /* 1123 */     sb.append("      ID of object defined in app config document.\n");
    /* 1124 */     sb.append("  <method>\n");
    /* 1125 */     sb.append("      Method to invoke on the specified object. Default: run.\n");
    /* 1126 */     sb.append("  [args]\n");
    /* 1127 */     sb.append("      Arguments to pass to the specified method. Only String\n");
    /* 1128 */     sb.append("      arguments are allowed. Methods requiring other\n");
    /* 1129 */     sb.append("      argument types may be wrapped in BeanShell script.\n");
    /* 1130 */     sb.append("\n");
    /* 1131 */     sb.append("Usage Examples:\n");
    /* 1132 */     sb.append("  java Controller --app-config AppConfig.xml BaselineUpdate run\n");
    /* 1133 */     sb.append("  java Controller --app-config AppConfig.xml LockManager releaseLock update_lock\n");
    /* 1134 */     sb.append("  java Controller --help --app-config AppConfig.xml Forge\n");
    /* 1135 */     sb.append("  java Controller --remove-app --app-config AppConfig.xml\n");
    /* 1136 */     sb.append("\n");
    /*      */ 
    /* 1138 */     System.out.println(sb.toString());
    /*      */   }
}
