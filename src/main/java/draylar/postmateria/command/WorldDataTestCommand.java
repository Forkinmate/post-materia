package draylar.postmateria.command;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import draylar.postmateria.PostMateria;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public class WorldDataTestCommand {

    public static void initialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
            LiteralCommandNode<ServerCommandSource> node = CommandManager.literal("worlddata")
                    .then(CommandManager.literal("wither")
                            .then(CommandManager.argument("killed", BoolArgumentType.bool())
                                    .executes(context -> {
                                        boolean killed = BoolArgumentType.getBool(context, "killed");
                                        PostMateria.getGlobalData(context.getSource().getServer(), PostMateria.WITHER_SLAIN_DATA).setWitherSlain(killed);
                                        return 1;
                                    }))).build();

            dispatcher.getRoot().addChild(node);
        });
    }
}
