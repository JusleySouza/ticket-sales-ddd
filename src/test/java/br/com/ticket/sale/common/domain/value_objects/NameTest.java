package br.com.ticket.sale.common.domain.value_objects;

import br.com.ticket.sale.core.common.domain.value_objects.Name;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NameTest {

    @Test
    void mustCreateAValidName() {
        Name name = new Name("Martha");
        assertThat(name.getValue()).isEqualTo("Martha");
    }
}
