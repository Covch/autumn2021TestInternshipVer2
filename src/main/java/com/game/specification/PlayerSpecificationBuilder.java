package com.game.specification;

import com.game.entity.Player;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PlayerSpecificationBuilder {
    private final List<Specification<Player>> list = new ArrayList<>();

    public PlayerSpecificationBuilder() {
    }

    public final PlayerSpecificationBuilder with(Specification<Player> specification) {
        list.add(specification);
        return this;
    }

    public Specification<Player> build() {
        Specification<Player> specification = null;
        if (list.size() > 0) {
            specification = list.remove(0);

        }
        if (list.size() > 0) {
            for (Specification<Player> currentSpec: list
                 ) {
                specification = specification.and(currentSpec);
            }
        }
        return specification;
    }

    public void clean() {
        this.list.clear();
    }

    @Override
    public String toString() {
        return "PlayerSpecificationBuilder{" +
                "list=" + list +
                '}';
    }
}
