package com.zhulin.controller;

import com.zhulin.bean.JsonModel;
import com.zhulin.dto.BookParams;
import com.zhulin.dto.PageDto;
import com.zhulin.entities.Book;
import com.zhulin.service.BookService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Author:ZHULIN
 * @Description:
 * @Date: 2022/4/5
 */
@RestController
@Slf4j
@Api(tags = "书本模块")
@RequestMapping(value = "/provide")
public class BookController {
    @Resource
    private BookService bookService;

    @ApiOperation("前台获取所有图书信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "查询成功"),
            @ApiResponse(code = 444, message = "查询失败")
    })
    @GetMapping("/getBooks/{pageIndex}/{pageSize}")
    public JsonModel<Map<String, Object>> getBooks(@PathVariable int pageIndex, @PathVariable int pageSize) {
        //存储传输分页信息
        PageDto pageDto = new PageDto(pageIndex, pageSize);
        Map<String, Object> books = bookService.getBookList(pageDto);
        if (books != null && books.size() > 0) {
            return new JsonModel<>(200, "查询成功", books);
        }
        return new JsonModel<>(444, "查询失败");
    }

    @ApiOperation("后台获取所有图书信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "查询成功"),
            @ApiResponse(code = 444, message = "查询失败")
    })
    @GetMapping("/getBooksRoot/{pageIndex}/{pageSize}")
    public JsonModel<Map<String, Object>> getBooksRoot(@PathVariable int pageIndex, @PathVariable int pageSize) {
        //存储传输分页信息
        PageDto pageDto = new PageDto(pageIndex, pageSize);
        Map<String, Object> books = bookService.getBookListRoot(pageDto);
        if (books != null && books.size() > 0) {
            return new JsonModel<>(200, "查询成功", books);
        }
        return new JsonModel<>(444, "查询失败");
    }

    @ApiOperation("根据书名查询书本信息")
    @ApiImplicitParam(name = "bookName", value = "书本名", required = true)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "查询成功"),
            @ApiResponse(code = 444, message = "查询失败")})
    @GetMapping("/getBookByName/{bookName}")
    public JsonModel<Book> getBookByName(@PathVariable String bookName) {
        Book book = bookService.getBookByName(bookName);
        if (book != null) {
            return new JsonModel<>(200, "查询成功", book);
        }
        return new JsonModel<>(444, "查询失败", null);
    }

    @ApiOperation("模糊查询书籍")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "nameOrClasses", value = "关键字", required = true),
            @ApiImplicitParam(name = "pageIndex", value = "具体页数", required = true),
            @ApiImplicitParam(name = "pageSize", value = "页大小", required = true)})
    @GetMapping("/getBookListByNameOrByClasses/{nameOrClasses}/{pageIndex}/{pageSize}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "查询成功"),
            @ApiResponse(code = 444, message = "查询失败")})
    public JsonModel<Map<String, Object>> getBookListByNameOrByClasses(@PathVariable String nameOrClasses,
                                                                       @PathVariable int pageIndex,
                                                                       @PathVariable int pageSize) {

        PageDto pageDto = new PageDto(pageIndex, pageSize);
        Map<String, Object> bookMap = bookService.getBookListByNameOrByClasses(nameOrClasses, pageDto);
        if (bookMap != null && bookMap.size() > 0) {
            return new JsonModel<>(200, "查询成功", bookMap);
        }
        return new JsonModel<>(444, "查询失败");
    }

    @ApiOperation("修改书籍状态")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "bid", value = "书籍编号", required = true),
            @ApiImplicitParam(name = "state", value = "书籍状态", required = true)
    })
    @PostMapping("/alterBook/{bid}/{state}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "修改成功"),
            @ApiResponse(code = 444, message = "修改状态失败")})
    public JsonModel alterBookState(@PathVariable int bid, @PathVariable int state) {
        if (bookService.alterBookState(bid, state)) {
            return new JsonModel(200, "修改成功");
        }
        return new JsonModel(444, "修改状态失败");
    }

    @ApiOperation("修改书籍信息")
    @ApiImplicitParam(type = "body", dataType = "BookParams", name = "bookParams", value = "图书修改信息", required = true)
    @PostMapping("/alterBookInfo")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "修改成功"),
            @ApiResponse(code = 444, message = "修改图书信息失败")})
    public JsonModel alterBookInfo(@RequestBody BookParams bookParams) {
        if (bookService.alterBookInfo(bookParams.getBook(), bookParams.getBid())) {
            return new JsonModel(200, "修改成功");
        }
        return new JsonModel(444, "修改图书信息失败");
    }

    @ApiOperation("添加书籍")
    @ApiImplicitParam(paramType = "body", dataType = "Book", name = "book", value = "书本信息", required = true)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "添加成功"),
            @ApiResponse(code = 443, message = "书籍信息不能为空"),
            @ApiResponse(code = 444, message = "添加失败")})
    @PostMapping("/addNewBook")
    public JsonModel addNewBook(@RequestBody Book book) {
        if (book == null) {
            return new JsonModel(443, "书籍信息不能为空");
        }
        if (bookService.addNewBook(book)) {
            return new JsonModel(200, "添加成功");
        }
        return new JsonModel(444, "添加失败");
    }

    @GetMapping("/getBookById/{id}")
    @ApiOperation("根据编号查询图书")
    @ApiImplicitParam(name = "id", value = "图书编号", required = true)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "查询成功"),
            @ApiResponse(code = 444, message = "查询失败")
    })
    public JsonModel<Book> getBookById(@PathVariable int id) {

        Book book = bookService.getBookById(id);
        if (book != null) {
            return new JsonModel<>(200, "查询成功", book);
        }
        return new JsonModel<>(444, "查询失败");
    }
}
