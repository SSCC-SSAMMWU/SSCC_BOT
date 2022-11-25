package utils;

import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

public class ButtonInterection {

    public static void all_search(ButtonInteractionEvent event) {
        SelectedMenu.getAllOptions(event, "http://localhost:8000/api/book");
    }

    public static void field_search(ButtonInteractionEvent event) {
        SelectedMenu.byField(event);
//        System.out.println(event.getMessage().getContentRaw().split("`` | ``")[2].replace("``", ""));
//        SelectedMenu.getAllOptions(event, "http://localhost:8000/api/book");
    }

    public static void title_search(ButtonInteractionEvent event) {
        SelectedMenu.getAllOptions(event, ("http://localhost:8000/api/book/" + event.getMessage().getContentRaw().split("`` | ``")[2].replace("``", "")));
    }
}
