package draylar.postmateria.command;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import draylar.postmateria.world.PhantasmaMeteorFeature;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.math.BlockPos;

public class GenerateCommand {

    public static void initialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
            LiteralCommandNode<ServerCommandSource> generate = CommandManager.literal("generate")
                    .then(CommandManager.literal("meteor")
                            .executes(context -> {
                                PhantasmaMeteorFeature.generate(context.getSource().getWorld(), raycast(context));
                                return 1;
                            })).build();

            dispatcher.getRoot().addChild(generate);
        });
    }

    public static BlockPos raycast(CommandContext<ServerCommandSource> source) throws CommandSyntaxException {
        return new BlockPos(source.getSource().getPlayer().raycast(256, 0, false).getPos());
    }
}
