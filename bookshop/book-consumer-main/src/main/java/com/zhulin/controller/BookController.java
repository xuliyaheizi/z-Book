package com.zhulin.controller;

import com.zhulin.bean.JsonModel;
import com.zhulin.constants.UrlConstant;
import com.zhulin.dto.BookParams;
import com.zhulin.entities.Book;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @Program:bookshop
 * @author：ZHULIN
 * @Description: @Create：2022/4/6
 */
@RestController
@Slf4j
public class BookController {

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/book/getBooks/{pageIndex}/{pageSize}")
    public JsonModel getBooKs(@PathVariable int pageIndex, @PathVariable int pageSize) {
        return restTemplate.getForObject(UrlConstant.BASE_URL + "/getBooks/" + pageIndex + "/" + pageSize,
                JsonModel.class);
    }

    @GetMapping("/book/getBooksRoot/{pageIndex}/{pageSize}")
    public JsonModel getBooKsRoot(@PathVariable int pageIndex, @PathVariable int pageSize) {
        return restTemplate.getForObject(UrlConstant.BASE_URL + "/getBooksRoot/" + pageIndex + "/" + pageSize,
                JsonModel.class);
    }

    @GetMapping("/book/getBookByName/{bookName}")
    public JsonModel<Book> getBookByName(@PathVariable String bookName) {
        return restTemplate.getForObject(UrlConstant.BASE_URL + "/getBookByName/" + bookName, JsonModel.class);
    }

    @GetMapping("/book/searchBook/{nameOrClasses}/{pageIndex}/{pageSize}")
    public JsonModel getBookListByNameOrByClasses(@PathVariable String nameOrClasses, @PathVariable int pageIndex,
                                                  @PathVariable int pageSize) {
        return restTemplate.getForObject(UrlConstant.BASE_URL + "/getBookListByNameOrByClasses/" + nameOrClasses +
                        "/" + pageIndex + "/" + pageSize,
                JsonModel.class);
    }

    @PostMapping("/book/alertBookState/{bid}/{state}")
    public JsonModel alterBookState(@PathVariable int bid, @PathVariable int state) {
        return restTemplate.postForObject(UrlConstant.BASE_URL + "/alterBook/" + bid + "/" + state, null,
                JsonModel.class);
    }

    @PostMapping("/book/alterBookInfo")
    public JsonModel alterBookInfo(@RequestBody BookParams bookParams) {
        return restTemplate.postForObject(UrlConstant.BASE_URL + "/alterBookInfo", bookParams, JsonModel.class);
    }

    @PostMapping("/book/addNewBook")
    public JsonModel addNewBook(@RequestBody Book book) {
        return restTemplate.postForObject(UrlConstant.BASE_URL + "/addNewBook", book, JsonModel.class);
    }

    @GetMapping("/book/getBookByID/{id}")
    public JsonModel getBookById(@PathVariable int id) {
        return restTemplate.getForObject(UrlConstant.BASE_URL + "/getBookById/" + id, JsonModel.class);
    }
}
