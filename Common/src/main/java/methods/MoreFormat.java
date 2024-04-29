package methods;

import Others.ConfigSystem;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.chat.ComponentSerializer;

import java.util.*;

public class MoreFormat {

    public static TextComponent FormatMore(String message) {

        if (ConfigSystem.INSTANCE.getFormat().getBoolean("AdvancedFormat", true)) {
            final List<ReturnValue> functions = understand(message);
            TextComponent finalMessage = new TextComponent(""); // Create a final TextComponent
            ChatColor lastColor = null; // Track the last color used
            ChatColor defaultColor = ChatColor.WHITE; // Default color
            int lastIndex = 0; // Track the last index processed

            for (final ReturnValue returnValue : functions) {

                // Add the text before the function to the final message
                String preText = message.substring(lastIndex, returnValue.start);
                if (!preText.isEmpty()) {
                    TextComponent preComponent = new TextComponent(preText);
                    // Apply the last color used
                    if (lastColor != null)
                        preComponent.setColor(lastColor);
                    finalMessage.addExtra(preComponent);
                }

                final BaseComponent text = TextComponent.fromLegacyText(returnValue.function[returnValue.function.length - 1])[0]; // Convert to TextComponent
                // Determine if the part contains a color
                ChatColor color = determineColor(text);
                // Use the default color if no color is found
                lastColor = Objects.requireNonNullElse(color, defaultColor);

                // Apply other formats if needed
                for (int i = 0; i < returnValue.function.length - 1; i++) {
                    switch (returnValue.function[i].toLowerCase()) {
                        case "font":
                            text.setFont(returnValue.function[i + 1]);
                            break;
                        case "insert":
                            text.setInsertion(returnValue.function[i + 1]);
                            break;
                        case "hover_text":
                            text.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new BaseComponent[]{new TextComponent(returnValue.function[i + 1])}));
                            break;
                        case "open_url":
                            text.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, returnValue.function[i + 1]));
                            break;
                        case "run_command":
                            text.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, returnValue.function[i + 1]));
                            break;
                        case "suggest_command":
                            text.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, returnValue.function[i + 1]));
                            break;
                        case "copy_to_clipboard":
                            text.setClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, returnValue.function[i + 1]));
                            break;
                    }
                }

                finalMessage.addExtra(text); // Add the formatted part to the final message
                lastIndex = returnValue.end; // Update the last index processed
            }

            // Add the remaining part of the message to the final message
            String remainingText = message.substring(lastIndex);
            if (!remainingText.isEmpty()) {
                TextComponent remainingComponent = new TextComponent(remainingText);
                // Apply the last color used
                if (lastColor != null)
                    remainingComponent.setColor(lastColor);
                finalMessage.addExtra(remainingComponent);
            }

            String json = ComponentSerializer.toString(finalMessage);
            json = json.replace("\\u003c", "<").replace("\\u003e", ">");

            return new TextComponent(ComponentSerializer.parse(json));
        }
        return new TextComponent(message);
    }

    private static List<ReturnValue> understand(final String input) {
        final char[] chars = input.toCharArray();

        final List<ReturnValue> functions = new ArrayList<>();
        for (int i = 0; i < chars.length - 1; i++) {
            if (chars[i] == '<' && chars[i + 1] == ';') {
                int j = i;
                final StringBuilder builder = new StringBuilder();
                while (j < chars.length - 1) {
                    builder.append(chars[j]);
                    j++;
                    if (chars[j] == '>') {
                        builder.append(">");
                        break;
                    }
                }

                if (!builder.isEmpty() && (builder.charAt(builder.length()-1) == '>' && builder.charAt(builder.length()-2) == ';')) {
                    String[] function = builder.toString().replace("<", "").replaceFirst(";", "").replace(">", "").split(";");
                    functions.add(new ReturnValue(i, j + 1, function));
                }
            }
        }
        return functions;
    }

    private static ChatColor determineColor(BaseComponent component) {
        if (component.getColor() != null)
            return component.getColor();

        // If it's a TextComponent, recursively check its children
        if (component instanceof TextComponent) {
            for (BaseComponent child : component.getExtra()) {
                ChatColor color = determineColor(child);
                if (color != null)
                    return color;
            }
        }

        return null;
    }

    private record ReturnValue(int start, int end, String[] function) {
    }
}
