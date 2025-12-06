package repository;

import model.Gift;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InMemoryGiftRepositoryTest {

    @Test
    void testMainFlow() {
        InMemoryGiftRepository repo = new InMemoryGiftRepository();

        Gift g1 = Gift.builder().title("G1").build();

        g1 = repo.save(g1);

        assertEquals(1, repo.findAll().size());

        Gift g2 = Gift.builder().id(100).title("G2").build();
        repo.save(g2);
        assertNotNull(repo.findById(100));

        assertNotNull(repo.findById(g1.getId()));
        assertNull(repo.findById(999));

        Gift updated = g1.toBuilder().title("Updated").build();
        assertTrue(repo.update(updated));

        assertFalse(repo.update(Gift.builder().id(555).title("F").build()));
    }
}