package com.game.service;

import com.game.controller.PlayerOrder;
import com.game.entity.Player;
import com.game.repository.PlayerRepository;
import com.game.specification.PlayerSpecification;
import com.game.specification.PlayerSpecificationBuilder;
import com.game.specification.SearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

//    public ResponseEntity<List<Player>> getAll (List<SearchCriteria> filters, Pageable pageable) {
//        Page<Player> pagePlayers;
//        pagePlayers = playerSpecification.getQueryResult(filters, pageable);
//
//        List<Player> players;
//        players = pagePlayers.getContent();
//
//        return new ResponseEntity<>(players, HttpStatus.OK);
//    }

//    public ResponseEntity<Integer> getPlayersCount(List<SearchCriteria> filters, Pageable pageable) {
//
//    }

    public List<Player> getAllWithFilters(Map<String, String> allParams) {
        int pageNumber, pageSize;
        PlayerOrder playerOrder;
        if (allParams.containsKey("pageNumber")) {
            pageNumber = Integer.parseInt(allParams.get("pageNumber"));
            allParams.remove("pageNumber");
        } else {
            pageNumber = 0;
        }
        if (allParams.containsKey("pageSize")) {
            pageSize = Integer.parseInt(allParams.get("pageSize"));
            allParams.remove("pageSize");
        } else  {
            pageSize = 3;
        }
        if (allParams.containsKey("playOrder")) {
            playerOrder = PlayerOrder.valueOf(allParams.get("playOrder"));
            allParams.remove("playOrder");
        } else {
            playerOrder = PlayerOrder.ID;
        }

        return playerRepository.findAll(getPageable(pageNumber, pageSize, playerOrder)).getContent();

    }

    private Pageable getPageable (int pageNumber, int pageSize, PlayerOrder playerOrder) {
        Sort sort = Sort.by(playerOrder.getFieldName());
        return PageRequest.of(pageNumber, pageSize, sort);
    }

    private Specification<Player> getSpecification(Map<String, String> allParams) {
        PlayerSpecificationBuilder playerSpecificationBuilder = new PlayerSpecificationBuilder();
        for (Map.Entry<String, String> entry: allParams.entrySet()
             ) {
            if (entry.getValue() != null) {
                playerSpecificationBuilder.with(new PlayerSpecification(new SearchCriteria(entry.getKey(), entry.getValue())));
            }
        }
        return null;
    }
}
