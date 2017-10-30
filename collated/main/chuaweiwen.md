# chuaweiwen
###### \java\seedu\address\MainApp.java
``` java
    @Subscribe
    public void handleChangeThemeRequestEvent(ChangeThemeRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        String filePath = "view/" + event.theme.getCss();
        userPrefs.setThemeFilePath(filePath);
    }
```
