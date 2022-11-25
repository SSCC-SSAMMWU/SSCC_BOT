package utils;

import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;

public class Command {
    public static void slashCommand(CommandListUpdateAction cmd) {
        cmd.addCommands(
                Commands.slash("대출", "SSCC 동방에 있는 도서를 대출합니다")
                        .addOptions(new OptionData(OptionType.STRING, "검색어", "책을 찾기 쉽게 검색어를 입력합니다")),
                Commands.slash("반납", "도서를 반납합니다")
        ).queue();
    }
}
