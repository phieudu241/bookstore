package dunglq.bookstore.controller;

import dunglq.bookstore.model.Book;
import dunglq.bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
@RequestMapping(value = {"/books"})
public class BookController {
    @Autowired
    BookRepository bookRepository;

    @RequestMapping(value = "")
    public String getBooks(Map<String, Object> model) {
        List<Book> books = StreamSupport.stream(bookRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
        model.put("books", books);
        return "books";
    }

    @RequestMapping(value = "/{id}")
    public String getBook(
            @PathVariable Integer id,
            Map<String, Object> model) {
        model.put("book", bookRepository.findOne(id));
        return "book";
    }

    @RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
    public String updateBook(
            @PathVariable Integer id,
            Book model) {

        Book book = bookRepository.findOne(id);
        book.setTitle(model.getTitle());
        book.setAuthor(model.getAuthor());
        book.setDescription(model.getDescription());
        book.setCreatedDate(new Date());
        bookRepository.save(book);

        return "redirect/books";
    }

    @RequestMapping(value = "/{id}/delete")
    public String deleteBook(
            @PathVariable Integer id) {
        bookRepository.delete(id);
        return "redirect:/books";
    }
}
