package fr.univ_amu.m1info.board_game_library;

import fr.univ_amu.m1info.board_game_library.model.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PositionTest {

    @Test
    void testPositionCreation() {
        Position position = new Position(3, 4);

        assertEquals(3, position.getRow(), "La ligne doit être 3");
        assertEquals(4, position.getCol(), "La colonne doit être 4");
    }

    @Test
    void testEqualsSameObject() {
        Position position = new Position(2, 5);

        assertTrue(position.equals(position), "Un objet doit être égal à lui-même");
    }

    @Test
    void testEqualsWithSameValues() {
        Position position1 = new Position(1, 1);
        Position position2 = new Position(1, 1);

        assertEquals(position1, position2, "Deux positions avec les mêmes valeurs doivent être égales");
    }

    @Test
    void testEqualsWithDifferentValues() {
        Position position1 = new Position(1, 1);
        Position position2 = new Position(2, 2);

        assertNotEquals(position1, position2, "Deux positions avec des valeurs différentes ne doivent pas être égales");
    }

    @Test
    void testEqualsWithNull() {
        Position position = new Position(3, 3);

        assertNotEquals(null, position, "Une position ne doit pas être égale à null");
    }

    @Test
    void testEqualsWithDifferentType() {
        Position position = new Position(3, 3);
        String notAPosition = "not a position";

        assertNotEquals(position, notAPosition, "Une position ne doit pas être égale à un objet d'un autre type");
    }

    @Test
    void testHashCode() {
        Position position1 = new Position(2, 3);
        Position position2 = new Position(2, 3);

        assertEquals(position1.hashCode(), position2.hashCode(),
                "Deux positions égales doivent avoir le même hashCode");
    }

    @Test
    void testToString() {
        Position position = new Position(4, 5);

        String expected = "Position{row=4, col=5}";
        assertEquals(expected, position.toString(), "La méthode toString doit retourner la représentation correcte");
    }
}
