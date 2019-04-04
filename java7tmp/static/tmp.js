$(document).ready(function() {
    setTimeout(function () {
        $("#swagger-ui > section > div.topbar > div > div > form > label > span").text("Version");
        $("#swagger-ui > section > div.swagger-ui > div > div.server-container > section > div > label").hide();
        $("#swagger-ui > section > div.swagger-ui > div > div.information-container.wrapper > section > div > hgroup > a > span").hide();
    }, 100);    
});
