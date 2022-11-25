package utils;

import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;

import java.util.HashMap;

public class SelectedMenu {

    public static void byField(ButtonInteractionEvent event) {
        event.getHook().deleteMessageById(event.getMessage().getId()).queue();
        event.reply("")
                .addActionRow(
                        StringSelectMenu.create("by_field")
                                .setPlaceholder("도서 탐색 범위를 선택해주세요")
                                .addOption("프로그래밍 관련 도서", "programing")
                                .addOption("전공 도서", "major")
                                .addOption("디자인 관련 도서", "design")
                                .addOption("기타 도서", "other")
                                .build()
                ).setEphemeral(true).queue();
    }

    public static void getAllOptions(ButtonInteractionEvent event, String url) {
        int cnt = 0;
        event.getHook().deleteMessageById(event.getMessage().getId()).queue();

        HashMap<Object, String> books = RequestApiGet.send(url);
        StringSelectMenu.Builder options = StringSelectMenu.create("select_book").setPlaceholder("대출할 책을 선택해주세요");
        for (Object i : books.keySet()) {
            if (cnt++ > 24) break;
            options.addOption(String.valueOf(books.get(i)), String.valueOf(i));
        }
        event.reply("최대 25권의 책만 선정됩니다.\n원하시는 책이 없는 경우 ``고급 검색`` 기능을 사용해주세요")
                .addActionRow(options.build()).setEphemeral(true).queue();
    }

    public static void getAllOptions(StringSelectInteractionEvent event, String url) {
        int cnt = 0;
        event.getHook().deleteMessageById(event.getMessage().getId()).queue();

        HashMap<Object, String> books = null;
        books = RequestApiGet.send(url);

        StringSelectMenu.Builder options = null;
        options = StringSelectMenu.create("select_book").setPlaceholder("대출할 책을 선택해주세요");
        for (Object i : books.keySet()) {
            if (cnt++ > 24) break;
            options.addOption(String.valueOf(books.get(i)), String.valueOf(i));
        }
//        책이 아예 없는 경우 예외처리해야됨
        event.reply("최대 25권의 책만 선정됩니다.\n원하시는 책이 없는 경우 ``고급 검색`` 기능을 사용해주세요")
                .addActionRow(options.build()).setEphemeral(true).queue();
    }
}
