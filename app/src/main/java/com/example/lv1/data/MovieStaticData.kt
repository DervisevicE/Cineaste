package com.example.lv1.data

fun favoriteMovies(): List<Movie>{
    return listOf(
        Movie(1,"Pride and prejudice", "Sparks fly when spirited Elizabeth Bennet meets single, rich, and proud Mr. Darcy. But Mr. Darcy reluctantly finds himself falling in love with a woman beneath his class. Can each overcome their own pride and prejudice?",
            "16.02.2005.","https://www.imdb.com/title/tt0414387/",
            "drama"),
        Movie(2,"Spy", "A desk-bound CIA analyst volunteers to go undercover to infiltrate the world of a deadly arms dealer, and prevent diabolical global disaster.",
        "15.06.2015", "https://www.imdb.com/title/tt3079380/", "comedy"),
        Movie(3,"The Lord of the Rings",
            "A meek Hobbit from the Shire and eight companions set out on a journey to destroy the powerful One Ring and save Middle-earth from the Dark Lord Sauron.",
            "10.12.2001","https://www.imdb.com/title/tt0120737/",
            "fantasy"),
        Movie(4, "Miss Congeniality", "An F.B.I. Agent must go undercover in the Miss United States beauty pageant to prevent a group from bombing the event.",
            "14.12.2000", "https://www.imdb.com/title/tt0212346/","comedy"),
        Movie(5, "Spider-Man: No Way Home", "With Spider-Man's identity now revealed, Peter asks Doctor Strange for help. When a spell goes wrong, dangerous foes from other worlds start to appear, forcing Peter to discover what it truly means to be Spider-Man",
        "17.12.2021", "https://www.imdb.com/title/tt10872600/", "action"),
        Movie(6,"Black Widow",
            "A film about Natasha Romanoff in her quests between the films Civil War and Infinity War.",
            "07.04.2021.","https://www.imdb.com/title/tt3480822/",
            "adventure")
    )
}

fun recentMovies(): List<Movie> {
    return listOf(
        Movie(1,"The Contractor",
            "A discharged U.S. Special Forces sergeant, James Harper, risks everything for his family when he joins a private contracting organization.",
            "01.04.2022.","https://www.imdb.com/title/tt10323676/",
            "thriller"),
        Movie(2, "Spider-Man: No Way Home", "With Spider-Man's identity now revealed, Peter asks Doctor Strange for help. When a spell goes wrong, dangerous foes from other worlds start to appear, forcing Peter to discover what it truly means to be Spider-Man",
            "17.12.2021", "https://www.imdb.com/title/tt10872600/", "action"),
        Movie(3, "Love tactics", "An ad executive and a fashion designer-blogger don't believe in love, so they place a bet to make the other fall head over heels - with unusual tactics.",
        "11.02.2022", "https://www.imdb.com/title/tt14486678/", "romance"),
        Movie(4, "The Batman", "When the Riddler, a sadistic serial killer, begins murdering key political figures in Gotham, Batman is forced to investigate the city's hidden corruption and question his family's involvement.",
        "04.03.2022", "https://www.imdb.com/title/tt1877830/","crime"),
        Movie(5, "The Adam Project", "After accidentally crash-landing in 2022, time-traveling fighter pilot Adam Reed teams up with his 12-year-old self for a mission to save the future.",
        "01.03.2022", "https://www.imdb.com/title/tt2463208/", "comedy")


    )
}

fun movieActors():Map<String,List<String>>{
    return mapOf<String,List<String>>("Pulp Fiction" to listOf("John Travolta","Samuel L. Jackson","Bruce Willis","Amanda Plummer","Laura Lovelace"),"Pride and prejudice" to listOf("Keira Knightley","Talulah Riley","Rosamund Pike"))
}

fun similarMovies():Map<String,List<String>>{
    return mapOf<String,List<String>>("Pulp Fiction" to listOf("Fight Club","Inception","Se7en"),"Pride and prejudice" to listOf("Jane Eyre","The Notebook","Atonement"))
}