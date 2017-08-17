package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashSet;
import java.util.Set;

@Controller
public class HomeController {

    @Autowired
    DirectorRepository directorRepository;

    @RequestMapping("/")
    public String index(Model model) {
        // Create an instance of Director
        Director director = new Director();
        director.setName("Stephen King");
        director.setGenre("Sci-Fi");

        // Create an instance of Movie
        Movie movie = new Movie();
        movie.setTitle("UFO: Myths or Legends?");
        movie.setYear(2018);
        movie.setDescription("Discovering the truths about who they really are...");

        // Add the movie to an empty list
        Set<Movie> movies = new HashSet<Movie>();
        movies.add(movie);

        // Add another movie
        movie = new Movie();
        movie.setTitle("The Life Beneath Our Foot");
        movie.setYear(2020);
        movie.setDescription("What really goes on underneath our surface?");
        movies.add(movie);

        // Add the list of movies to the director's movie list
        director.setMovies(movies);

        // Save the director to the database
        directorRepository.save(director);

        // Grab all the directors from the database and send to the template
        model.addAttribute("directors", directorRepository.findAll());
        return "index";
    }

}
