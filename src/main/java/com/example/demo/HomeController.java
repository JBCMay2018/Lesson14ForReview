package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@Controller
public class HomeController {

    @Autowired
    DirectorRepository directorRepository;

    @Autowired
    MovieRepository movieRepository;

    @RequestMapping("/")
    public String index(Model model) {
        // Create an instance of Director
//        Director director = new Director();
//        director.setName("Stephen King");
//        director.setGenre("Sci-Fi");
//        directorRepository.save(director);
//
//        // Create an instance of Movie
//        Movie movie = new Movie();
//        movie.setTitle("UFO: Myths or Legends?");
//        movie.setYear(2018);
//        movie.setDescription("Discovering the truths about who they really are...");
//        //movie.setDirector(director);
//        movieRepository.save(movie);
//        director.addMovie(movie);
//
//        // Add another movie
//        movie = new Movie();
//        movie.setTitle("The Life Beneath Our Foot");
//        movie.setYear(2020);
//        movie.setDescription("What really goes on underneath our surface?");
//        //movieB.setDirector(director);
//        movieRepository.save(movie);
//        director.addMovie(movie);
//
//        // Save the director to the database
//        directorRepository.save(director);
//        System.out.println(director.toString());

        // Grab all the directors from the database and send to the template
        model.addAttribute("directors", directorRepository.findAll());
        model.addAttribute("movies", movieRepository.findAll());
        return "index";
    }

    // MOVIE
    @GetMapping("/addmovie")
    public String loadMovieForm(Model model) {
        model.addAttribute("movie", new Movie());
        return "movieform";
    }

    @PostMapping("/processmovie")
    public String processMovieForm(@ModelAttribute("movie") Movie movie) {
        movieRepository.save(movie);
        return "redirect:/";
    }

    // DIRECTOR
    @GetMapping("/adddirector")
    public String loadDirectorForm(Model model) {
        model.addAttribute("director", new Director());
        return "directorform";
    }

    @PostMapping("/processdirector")
    public String processDirectorForm(@ModelAttribute("director") Director director) {
        directorRepository.save(director);
        return "redirect:/";
    }

    @GetMapping("/adddirectortomovie/{movieId}")
    public String addDirectorToMovie(@PathVariable("movieId") long movieId, Model model) {
        model.addAttribute("movie", movieRepository.findById(movieId).get());
        model.addAttribute("listOfDirectors", directorRepository.findAll());
        return "moviedirectorform";
    }

    @PostMapping("/adddirectortomovie/{movieId}")
    public String processDirectorToMovie(@RequestParam("directors") String directorId,
                                          @PathVariable("movieId") long movieId,
                                          @ModelAttribute("aDirector") Director d, Model model) {
        d = directorRepository.findById(new Long(directorId)).get();
        Movie m = movieRepository.findById(new Long(movieId)).get();
        m.setDirector(d);
        movieRepository.save(m);
        model.addAttribute("movieList", movieRepository.findAll());
        model.addAttribute("directorList", directorRepository.findAll());
        return "redirect:/";
    }

}
