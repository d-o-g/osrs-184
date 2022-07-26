package jag.statics;

import jag.commons.collection.NodeDeque;
import jag.game.menu.ComponentSelection;
import jag.game.InterfaceComponent;
import jag.game.client;
import jag.game.menu.ContextMenuBuilder;
import jag.game.scene.SceneGraph;
import jag.game.scene.entity.EntityUID;
import jag.game.scene.entity.NpcEntity;
import jag.game.scene.entity.Pickable;
import jag.game.scene.entity.PlayerEntity;
import jag.game.type.ItemDefinition;
import jag.game.type.ObjectDefinition;
import jag.graphics.IndexedSprite;
import jag.opcode.GPI;
import jag.script.ScriptEvent;

public class Statics49 {
    public static IndexedSprite titleboxSprite;

    public static char convertDiacritic(char var0) {
        switch (var0) {
            case ' ':
            case '-':
            case '_':
            case ' ':
                return '_';
            case '#':
            case '[':
            case ']':
                return var0;
            case 'À':
            case 'Á':
            case 'Â':
            case 'Ã':
            case 'Ä':
            case 'à':
            case 'á':
            case 'â':
            case 'ã':
            case 'ä':
                return 'a';
            case 'Ç':
            case 'ç':
                return 'c';
            case 'È':
            case 'É':
            case 'Ê':
            case 'Ë':
            case 'è':
            case 'é':
            case 'ê':
            case 'ë':
                return 'e';
            case 'Í':
            case 'Î':
            case 'Ï':
            case 'í':
            case 'î':
            case 'ï':
                return 'i';
            case 'Ñ':
            case 'ñ':
                return 'n';
            case 'Ò':
            case 'Ó':
            case 'Ô':
            case 'Õ':
            case 'Ö':
            case 'ò':
            case 'ó':
            case 'ô':
            case 'õ':
            case 'ö':
                return 'o';
            case 'Ù':
            case 'Ú':
            case 'Û':
            case 'Ü':
            case 'ù':
            case 'ú':
            case 'û':
            case 'ü':
                return 'u';
            case 'ß':
                return 'b';
            case 'ÿ':
            case 'Ÿ':
                return 'y';
            default:
                return Character.toLowerCase(var0);
        }
    }

}
