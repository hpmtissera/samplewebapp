package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController
{

    // @Autowired annotaiton search for a class that implements UserDao and create a new instance of that class and inject it here.
    // In our case Spring will automatically create a object of UserDaoImpl class and assign to userDao variable.
    // Since we use Spring we don't need to do UserDao userDao = new UserDaoImpl(); because Spring create the instance.
    @Autowired
    UserDao userDao;

    public MainController()
    {
    }

    /**
     * This method called when the user navigated to page http://localhost:8080/
     *
     * @param model
     * @return
     */
    @GetMapping("/")
    public String main(Model model)
    {
        User newUser = new User();
        model.addAttribute("user", newUser);
        model.addAttribute("users", userDao.getAll()); // read all the users from the database and add to the main page

        return "welcome"; // return the main page (welcome.html) (samplewebapp/src/main/resources/templates/welcome.html)
    }

    /**
     * This method called when click the Add User button (Goes to ttp://localhost:8080/add)
     *
     * @param user
     * @return
     */
    @PostMapping("/add")
    public String submitForm(@ModelAttribute("user") User user)
    {
        System.out.println(user); // print the user to the console
        userDao.add(user); // call add user method to insert the user into the h2 database
        return "redirect:/"; // go back to the home page (ttp://localhost:8080/) from (ttp://localhost:8080/add) page
    }
}
