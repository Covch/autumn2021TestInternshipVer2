package com.game.controller;

import com.game.entity.Player;
import com.game.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

//throw new ResponseStatusException(HttpStatus.NOT_FOUND, "IT IS WORKING???");

@RestController
@RequestMapping("/rest")
public class PlayersController {
    private final PlayerService playerService;

    @Autowired
    public PlayersController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/players")
    ResponseEntity<List<Player>> getPlayersList(@RequestParam Map<String, String> allParams) {
        return new ResponseEntity<>(playerService.getAllWithFilters(allParams), HttpStatus.OK);
    }

    @GetMapping("/players/count")
    ResponseEntity<Integer> getPlayersCount(@RequestParam Map<String, String> allParams) {
        return new ResponseEntity<>(playerService.getAllWithFiltersCount(allParams), HttpStatus.OK);
    }

    @GetMapping("/players/{id}")
    ResponseEntity<Player> getPlayerById(@PathVariable String id) {
        long idLong = 0;
        Optional<Player> player;
        if (!checkId(id)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            idLong = Long.parseLong(id);
            player = playerService.getPlayerById(idLong);
        }
        if (!player.isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(player.get(), HttpStatus.OK);
    }

    @PostMapping("/players")
    @ResponseBody
    ResponseEntity<Player> createPlayer(@RequestBody Player player) {
        if (checkPlayer(player, false)) {
            playerService.save(player);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(player, HttpStatus.OK);
    }

    @PostMapping("/players/{id}")
    ResponseEntity<?> updatePlayer(@RequestBody Player updatablePlayer, @PathVariable String id) {
        if (!checkPlayer(updatablePlayer, true)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Player player;
        if (getPlayerById(id).getStatusCode().equals(HttpStatus.OK)) {
            player = getPlayerById(id).getBody();
        } else {
            return new ResponseEntity<>(getPlayerById(id).getStatusCode());
        }
        player = playerService.update(updatablePlayer, player);
        return new ResponseEntity<>(player, HttpStatus.OK);
    }

    @DeleteMapping("/players/{id}")
    ResponseEntity<?> deletePlayer(@PathVariable String id) {
        if (checkId(id)) {
            ResponseEntity<Player> responseEntity = getPlayerById(id);
            if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
                playerService.delete(responseEntity.getBody());
            }
            return new ResponseEntity<>(responseEntity.getStatusCode());
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    private boolean checkPlayer(Player player, boolean isUpdating) {
        if (!isUpdating) {
            if (player.getName() == null
                    || player.getTitle() == null
                    || player.getRace() == null
                    || player.getProfession() == null
                    || player.getBirthday() == null
                    || player.getExperience() == null) {
                return false;
            }
        }
        if ((player.getName() != null) && player.getName().length() > 12
                || (player.getName() != null) && player.getName().trim().length() == 0
                || (player.getTitle() != null) && player.getTitle().length() > 30
                || (player.getExperience() != null) && (player.getExperience() < 0 || player.getExperience() > 10000000)
                || (player.getBirthday() != null) && player.getBirthday().getTime() < 946674000000L
                || (player.getBirthday() != null) && player.getBirthday().getTime() > 32535205199999L) {
            return false;
        }
        if (player.isBanned() == null) {
            player.setBanned(false);
        }
        return true;
    }

    private boolean checkId(String id) {
        try {
            long idLong = Long.parseLong(id);
            if (!(idLong > 0)) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
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

}
