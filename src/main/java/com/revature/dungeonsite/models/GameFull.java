package com.revature.dungeonsite.models;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GameFull {
    @Getter  @Setter
    private Game game;

    @Getter  @Setter
    private List<Address> addresses;

    @Getter  @Setter
    private List<Behavior> behaviors;

    @Getter  @Setter
    private List<Language> languages;

    @Getter  @Setter
    private List<Link> links;

    @Getter  @Setter
    private List<Schedule> schedules;

    public void setGame(Optional<Game> option) {
        if (option.isPresent())
            this.game = option.get();
    }

    public void setAddress(List<Optional<Address>> option) {
        List<Address> list = new ArrayList<>();

        for (Optional<Address> a : option) {
            if (a.isPresent()) {
                list.add(a.get());
            }
        }

        this.setAddresses(list);
    }

    public void setBehavior(List<Optional<Behavior>> option) {
        List<Behavior> list = new ArrayList<>();

        for (Optional<Behavior> item : option) {
            if (item.isPresent()) {
                list.add(item.get());
            }
        }

        this.setBehaviors(list);
    }

    public void setLanguage(List<Optional<Language>> option) {
        List<Language> list = new ArrayList<>();

        for (Optional<Language> item : option) {
            if (item.isPresent()) {
                list.add(item.get());
            }
        }

        this.setLanguages(list);
    }

    public void setLink(List<Optional<Link>> option) {
        List<Link> list = new ArrayList<>();

        for (Optional<Link> link : option) {
            if (link.isPresent()) {
                list.add(link.get());
            }
        }

        this.setLinks(list);
    }

    public void setSchedule(List<Optional<Schedule>> option) {
        List<Schedule> list = new ArrayList<>();

        for (Optional<Schedule> item : option) {
            if (item.isPresent()) {
                list.add(item.get());
            }
        }

        this.setSchedules(list);
    }

}
