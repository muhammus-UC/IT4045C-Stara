package com.stara.enterprise.dto;

import lombok.Data;

// Holds information about a Favorite Actor or Show to add to Favorites.
public @Data
class Favorite {
    /*
    String used to uniquely differentiate from other Favorites.
    This string does differentiate between an Actor and a Show by prepending either "Actor_" or "Show_".
    Meaning, a Show with id of 78 that has been added as a Favorite would have an id of "Show_78".
     */
    private String id;
    // String of name of Favorite - corresponds to name of Actor or Show being added as a Favorite.
    private String name;
    // String of detail of Favorite - for a Favorite Actor it is the Actor's Gender & for a Favorite Show it is the Show's Status
    private String subtitle;
    // String of subtitle of Favorite - for a Favorite Actor is the Actor's Country & for a Favorite Show it is the Show's Language.
    private String detail;
    // String of URL to image of Favorite Actor or Show
    private String image;
    // String of URL to TVMaze page of Favorite Actor or Show
    private String url;
}
