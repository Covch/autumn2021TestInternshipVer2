package com.game.controller;

import com.game.entity.Player;
import com.game.service.PlayerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

//throw new ResponseStatusException(HttpStatus.NOT_FOUND, "IT IS WORKING???");

@RestController
@RequestMapping("/rest")
public class PlayersController {
    private final PlayerService playerService;

    public PlayersController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/players")
    ResponseEntity<List<Player>> getPlayersList(@RequestParam Map<String, String> allParams) {
        return new ResponseEntity<>(playerService.getAllWithFilters(allParams), HttpStatus.OK);
    }

//    @GetMapping("/players/count")
//    ResponseEntity<Integer> getPlayersCount(@RequestParam Map<String, String> allParams) {
//        return playerService.getAllWithFiltersCount(allParams);
//    }
//
//    @GetMapping("/players/{id}")
//    ResponseEntity<Player> getPlayerById(@PathVariable String id) {
//        long idLong = 0;
//        Player player = null;
//        if (checkId(id)) {
//            idLong = Long.parseLong(id);
//            player = playerService.getById(idLong);
//        }
//        if (player == null)
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Player with provided ID not found.");
//        return player;
//    }
//
//    @PostMapping("/players")
//    @ResponseBody
//    ResponseEntity<Player> createPlayer(@RequestBody PlayerRequest playerRequest) {
//        Player player = new Player();
//        if (checkPlayerRequest(playerRequest, false)) {
//            player.setName(playerRequest.getName());
//            player.setTitle(playerRequest.getTitle());
//            player.setRace(Race.valueOf(playerRequest.getRace()));
//            player.setProfession(Profession.valueOf(playerRequest.getProfession()));
//            player.setBirthday(new Date(Long.parseLong(playerRequest.getBirthday())));
//            player.setBanned(playerRequest.getBanned() != null && Boolean.parseBoolean(playerRequest.getBanned()));
//            player.setExperience(Integer.parseInt(playerRequest.getExperience()));
//            player.setLevel(calculateLevel(player.getExperience()));
//            player.setUntilNextLevel(calculateExperienceTillNextLevel(player.getLevel(), player.getExperience()));
//        }
//        playerService.save(player);
//        return player;
//    }
//
//    @PostMapping("/players/{id}")
//    ResponseEntity<?> updatePlayer(@RequestBody PlayerRequest playerRequest, @PathVariable String id) {
//        checkPlayerRequest(playerRequest, true);
//        Player player = getPlayerById(id);
//        if (playerRequest.getName() != null) player.setName(playerRequest.getName());
//        if (playerRequest.getTitle() != null) player.setTitle(playerRequest.getTitle());
//        if (playerRequest.getRace() != null) player.setRace(Race.valueOf(playerRequest.getRace()));
//        if (playerRequest.getProfession() != null) player.setProfession(Profession.valueOf(playerRequest.getProfession()));
//        if (playerRequest.getBirthday() != null) player.setBirthday(new Date(Long.parseLong(playerRequest.getBirthday())));
//        if (playerRequest.getBanned() != null) player.setBanned(Boolean.parseBoolean(playerRequest.getBanned()));
//        if (playerRequest.getExperience() != null) {
//            player.setExperience(Integer.parseInt(playerRequest.getExperience()));
//            player.setLevel(calculateLevel(player.getExperience()));
//            player.setUntilNextLevel(calculateExperienceTillNextLevel(player.getLevel(), player.getExperience()));
//        }
//        playerService.update(player);
//        return player;
//    }
//
//
//    @DeleteMapping("/players/{id}")
//    ResponseEntity<?> deletePlayer(@PathVariable String id) {
//        checkId(id);
//        playerService.delete(getPlayerById(id));
//    }
//
//    private boolean checkId(String id) {
//        try {
//            long idLong = Long.parseLong(id);
//            if (!(idLong > 0)) {
//                throw new NumberFormatException();
//            }
//        } catch (NumberFormatException e) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect ID field.");
//        }
//        return true;
//    }
//
//    private boolean checkPlayerRequest(PlayerRequest playerRequest, boolean isUpdating) {
//        // May be problems with "null" experience
//        if (!isUpdating) {
//            if (playerRequest.getName() == null || playerRequest.getTitle() == null || playerRequest.getRace() == null ||
//                    playerRequest.getProfession() == null || playerRequest.getBirthday() == null || playerRequest.getExperience() == null) {
//                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "URL Data is incomplete.");
//            }
//        }
//        if (playerRequest.getName() != null && playerRequest.getTitle() != null && (playerRequest.getName().length() > 12 || playerRequest.getTitle().length() > 30)) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "'name' or (and) 'title' is (are) too long.");
//        }
//        if (playerRequest.getName() != null && playerRequest.getName().isEmpty()) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "'name' is empty.");
//        }
//        if (playerRequest.getExperience() != null && (Integer.parseInt(playerRequest.getExperience()) < 0 || Integer.parseInt(playerRequest.getExperience()) > 10_000_000)) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "'experience' is out of bounds.");
//        }
//        if (playerRequest.getBirthday() != null && (Long.parseLong(playerRequest.getBirthday()) < 0)) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect registration date.");
//        }
//        if (playerRequest.getBirthday() != null && (Long.parseLong(playerRequest.getBirthday()) < 946674000000L || Long.parseLong(playerRequest.getBirthday()) > 32535205199999L)) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect registration date.");
//        }
//        return true;
//    }
//
//    private int calculateLevel(int experience) {
//        return (int) ((Math.sqrt(2500 + 200.0 * experience) - 50) / 100);
//    }
//
//    private int calculateExperienceTillNextLevel(int currentLvl, int experience) {
//        return 50 * (currentLvl + 1) * (currentLvl + 2) - experience;
//    }
}
