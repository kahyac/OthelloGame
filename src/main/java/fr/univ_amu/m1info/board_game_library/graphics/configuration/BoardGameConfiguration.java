package fr.univ_amu.m1info.board_game_library.graphics.configuration;

import java.util.List;

public record BoardGameConfiguration(String title,
                                     BoardGameDimensions dimensions,
                                     List<LabeledElementConfiguration> labeledElementConfigurations) {
}
