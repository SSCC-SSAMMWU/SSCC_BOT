package utils;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import static net.dv8tion.jda.api.interactions.components.buttons.Button.*;
import static net.dv8tion.jda.api.interactions.components.buttons.Button.primary;

public class Buttons {
    public static void argsSearch(SlashCommandInteractionEvent event, String arg) {
        event.reply("SSCC에 대출되지 않은 책들을 검색할 수 있습니다\n``고급 검색`` | " + "``" + arg + "``")
                .addActionRow(
                        secondary("takeout:all", "모두 보기"),
                        success("takeout:by_field", "분야별 검색"),

                        primary("takeout:by_title", "책 이름 검색"),
                        primary("takeout:by_author", "저자 검색")
                ).setEphemeral(true)
                .queue();
    }

    public static void notArgsSearch(SlashCommandInteractionEvent event) {
        event.reply("SSCC에 대출되지 않은 책들을 검색할 수 있습니다")
                .addActionRow(
                        secondary("takeout:all", "모두 보기"),
                        success("takeout:by_field", "분야별 검색")
                ).setEphemeral(true)
                .queue();
    }
}
