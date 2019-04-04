package SQLServer;

public class generateCreateDBSQL3 {
    public static void main(String[] args) {
        // update this number when you want to create another new database schema
        System.out.println(generateSQL("_test",false));
    }

   private static String generateSQL(String pSchemaSuffix, boolean pNeedCommit){
       StringBuilder sb = new StringBuilder();
       sb.append("-------execute sql below----------------------").append("\n");
       sb.append("USE [master]").append("\n");
       sb.append("DROP LOGIN ACDX_EXPORT_ACRS_DEV"+pSchemaSuffix).append("\n");
       sb.append("DROP LOGIN ACDX_IMPORT_ACRS_DEV"+pSchemaSuffix).append("\n");
       sb.append("DROP LOGIN ATGCATA_ACRS_DEV"+pSchemaSuffix).append("\n");
       sb.append("DROP LOGIN ATGCATB_ACRS_DEV"+pSchemaSuffix).append("\n");
       sb.append("DROP LOGIN ATGCORE_ACRS_DEV"+pSchemaSuffix).append("\n");
       sb.append("DROP LOGIN ATGPUB_ACRS_DEV"+pSchemaSuffix).append("\n");
       sb.append("CREATE LOGIN ACDX_EXPORT_ACRS_DEV"+pSchemaSuffix+" WITH PASSWORD=N'ACDX_EXPORT_ACRS_DEV"+pSchemaSuffix+"', DEFAULT_DATABASE=[BEACON_DEV"+pSchemaSuffix+"], CHECK_EXPIRATION=OFF, CHECK_POLICY=OFF;").append("\n");
       sb.append("CREATE LOGIN ACDX_IMPORT_ACRS_DEV"+pSchemaSuffix+" WITH PASSWORD=N'ACDX_IMPORT_ACRS_DEV"+pSchemaSuffix+"', DEFAULT_DATABASE=[BEACON_DEV"+pSchemaSuffix+"], CHECK_EXPIRATION=OFF, CHECK_POLICY=OFF;").append("\n");
       sb.append("CREATE LOGIN ATGCATA_ACRS_DEV"+pSchemaSuffix+" WITH PASSWORD=N'ATGCATA_ACRS_DEV"+pSchemaSuffix+"', DEFAULT_DATABASE=[BEACON_DEV"+pSchemaSuffix+"], CHECK_EXPIRATION=OFF, CHECK_POLICY=OFF;").append("\n");
       sb.append("CREATE LOGIN ATGCATB_ACRS_DEV"+pSchemaSuffix+" WITH PASSWORD=N'ATGCATB_ACRS_DEV"+pSchemaSuffix+"', DEFAULT_DATABASE=[BEACON_DEV"+pSchemaSuffix+"], CHECK_EXPIRATION=OFF, CHECK_POLICY=OFF;").append("\n");
       sb.append("CREATE LOGIN ATGCORE_ACRS_DEV"+pSchemaSuffix+" WITH PASSWORD=N'ATGCORE_ACRS_DEV"+pSchemaSuffix+"', DEFAULT_DATABASE=[BEACON_DEV"+pSchemaSuffix+"], CHECK_EXPIRATION=OFF, CHECK_POLICY=OFF;").append("\n");
       sb.append("CREATE LOGIN ATGPUB_ACRS_DEV"+pSchemaSuffix+" WITH PASSWORD=N'ATGPUB_ACRS_DEV"+pSchemaSuffix+"', DEFAULT_DATABASE=[BEACON_DEV"+pSchemaSuffix+"], CHECK_EXPIRATION=OFF, CHECK_POLICY=OFF;").append("\n");
       sb.append("GO").append("\n");
       if(pNeedCommit) {
           sb.append("commit;").append("\n");
       }
       sb.append("-------execute sql below----------------------").append("\n");
       sb.append("USE [master]").append("\n");
       sb.append("GO").append("\n");
       sb.append("DROP USER ACDX_EXPORT_ACRS_DEV"+pSchemaSuffix).append("\n");
       sb.append("CREATE USER ACDX_EXPORT_ACRS_DEV"+pSchemaSuffix+" FOR LOGIN ACDX_EXPORT_ACRS_DEV"+pSchemaSuffix+";").append("\n");
       sb.append("ALTER USER ACDX_EXPORT_ACRS_DEV"+pSchemaSuffix+" WITH DEFAULT_SCHEMA=[dbo];").append("\n");
       sb.append("\n");
       sb.append("DROP USER ACDX_IMPORT_ACRS_DEV"+pSchemaSuffix).append("\n");
       sb.append("CREATE USER ACDX_IMPORT_ACRS_DEV"+pSchemaSuffix+" FOR LOGIN ACDX_IMPORT_ACRS_DEV"+pSchemaSuffix+";").append("\n");
       sb.append("ALTER USER ACDX_IMPORT_ACRS_DEV"+pSchemaSuffix+" WITH DEFAULT_SCHEMA=[dbo];").append("\n");
       sb.append("\n");
       sb.append("DROP USER ATGCATA_ACRS_DEV"+pSchemaSuffix).append("\n");
       sb.append("CREATE USER ATGCATA_ACRS_DEV"+pSchemaSuffix+" FOR LOGIN ATGCATA_ACRS_DEV"+pSchemaSuffix+";").append("\n");
       sb.append("ALTER USER ATGCATA_ACRS_DEV"+pSchemaSuffix+" WITH DEFAULT_SCHEMA=[dbo];").append("\n");
       sb.append("\n");
       sb.append("DROP USER ATGCATB_ACRS_DEV"+pSchemaSuffix).append("\n");
       sb.append("CREATE USER ATGCATB_ACRS_DEV"+pSchemaSuffix+" FOR LOGIN ATGCATB_ACRS_DEV"+pSchemaSuffix+";").append("\n");
       sb.append("ALTER USER ATGCATB_ACRS_DEV"+pSchemaSuffix+" WITH DEFAULT_SCHEMA=[dbo];").append("\n");
       sb.append("\n");
       sb.append("DROP USER ATGCORE_ACRS_DEV"+pSchemaSuffix).append("\n");
       sb.append("CREATE USER ATGCORE_ACRS_DEV"+pSchemaSuffix+" FOR LOGIN ATGCORE_ACRS_DEV"+pSchemaSuffix+";").append("\n");
       sb.append("ALTER USER ATGCORE_ACRS_DEV"+pSchemaSuffix+" WITH DEFAULT_SCHEMA=[dbo];").append("\n");
       sb.append("\n");
       sb.append("DROP USER ATGPUB_ACRS_DEV"+pSchemaSuffix).append("\n");
       sb.append("CREATE USER ATGPUB_ACRS_DEV"+pSchemaSuffix+" FOR LOGIN ATGPUB_ACRS_DEV"+pSchemaSuffix+";").append("\n");
       sb.append("ALTER USER ATGPUB_ACRS_DEV"+pSchemaSuffix+" WITH DEFAULT_SCHEMA=[dbo];").append("\n");
       sb.append("GO").append("\n");
       if(pNeedCommit) {
           sb.append("commit;").append("\n");
       }
       sb.append("-------execute sql below----------------------").append("\n");
       sb.append("USE BEACON_DEV"+pSchemaSuffix).append("\n");
       sb.append("exec sp_change_users_login 'UPDATE_ONE','ACDX_EXPORT','ACDX_EXPORT_ACRS_DEV"+pSchemaSuffix+"';").append("\n");
       sb.append("GO").append("\n");
       if(pNeedCommit) {
           sb.append("commit;").append("\n");
       }
       sb.append("USE BEACON_DEV"+pSchemaSuffix).append("\n");
       sb.append("exec sp_change_users_login 'UPDATE_ONE','ACDX_IMPORT','ACDX_IMPORT_ACRS_DEV"+pSchemaSuffix+"';").append("\n");
       sb.append("GO").append("\n");
       if(pNeedCommit) {
           sb.append("commit;").append("\n");
       }
       sb.append("USE BEACON_DEV"+pSchemaSuffix).append("\n");
       sb.append("exec sp_change_users_login 'UPDATE_ONE','ATGCATA_ACRS','ATGCATA_ACRS_DEV"+pSchemaSuffix+"';").append("\n");
       sb.append("GO").append("\n");
       if(pNeedCommit) {
           sb.append("commit;").append("\n");
       }
       sb.append("USE BEACON_DEV"+pSchemaSuffix).append("\n");
       sb.append("exec sp_change_users_login 'UPDATE_ONE','ATGCATB_ACRS','ATGCATB_ACRS_DEV"+pSchemaSuffix+"';").append("\n");
       sb.append("GO").append("\n");
       if(pNeedCommit) {
           sb.append("commit;").append("\n");
       }
       sb.append("USE BEACON_DEV"+pSchemaSuffix).append("\n");
       sb.append("exec sp_change_users_login 'UPDATE_ONE','ATGCORE_ACRS','ATGCORE_ACRS_DEV"+pSchemaSuffix+"';").append("\n");sb.append("GO").append("\n");
       if(pNeedCommit) {
           sb.append("commit;").append("\n");
       }
       sb.append("USE BEACON_DEV"+pSchemaSuffix).append("\n");
       sb.append("exec sp_change_users_login 'UPDATE_ONE','ATGPUB_ACRS','ATGPUB_ACRS_DEV"+pSchemaSuffix+"';").append("\n");
       sb.append("GO").append("\n");
       if(pNeedCommit) {
           sb.append("commit;").append("\n");
       }
       sb.append("---------end--------------------").append("\n");
       sb.append("-------Please update /project/scripts/<your name>.properties with follow:").append("\n");
       sb.append("-------database.postfix=ACRS_DEV"+pSchemaSuffix).append("\n");
       sb.append("-------database.name=BEACON_DEV"+pSchemaSuffix).append("\n");

       return sb.toString();
   }

}
