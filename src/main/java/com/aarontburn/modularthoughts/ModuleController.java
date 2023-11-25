package com.aarontburn.modularthoughts;


import com.aarontburn.modularthoughts.home_module.HomeModule;
import com.aarontburn.modularthoughts.module.Module;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class ModuleController extends Application {


    private final List<Module> moduleList = new ArrayList<>();

    private static GUIHandler GUI_HANDLER;

    @Override
    public void start(final Stage stage) throws Exception {
        GUI_HANDLER = new GUIHandler(stage);
        GUI_HANDLER.setOnExit(() -> {
            for (final Module module : moduleList) {
                module.stop();
            }
        });
        // start the gui, but don't display the window yet

        // Figure out what modules are active
        registerModule(new HomeModule());

        for (final Module m : moduleList) {
            if (m.getClass() == HomeModule.class) {
                m.getGUI().show();
            }
        }

        GUI_HANDLER.show();
    }

    public static GUIHandler getGuiHandler() {
        return GUI_HANDLER;
    }

    private void registerModule(final Module module) {
        moduleList.add(module);
    }


//    private void parseEnabledModulesJson() {
//
//        final String jsonFilePath
//                = Objects.requireNonNull(Main.class.getResource("enabled-modules.json")).getFile();
//
//        try (final Reader reader = new FileReader(jsonFilePath)) {
//            final Type type = new TypeToken<Map<String, Object>>() {}.getType();
//            final Map<String, Object> map = new Gson().fromJson(reader, type);
//
//
//            for (final String key : map.keySet()) {
//                final String s = ((String) map.get(key)).replace(" ", "");
//                final String[] modules = s.split(",");
//
//                for (final String moduleString : modules) {
//                    moduleList.add(Class.forName(moduleString));
//
//                }
//            }
//
//        } catch (IOException e) {
//            Logger.log(e);
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//        System.out.println(moduleList);
//
//
//    }
}
