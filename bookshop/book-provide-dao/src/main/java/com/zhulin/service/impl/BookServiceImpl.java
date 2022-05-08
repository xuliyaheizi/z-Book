package com.zhulin.service.impl;

import com.zhulin.dao.BookDao;
import com.zhulin.dto.PageDto;
import com.zhulin.entities.Book;
import com.zhulin.service.BookService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author:ZHULIN
 * @Description:
 * @Date: 2022/4/5
 */
@Service
public class BookServiceImpl implements BookService {
    @Resource
    private BookDao bookDao;

    @Override
    public Map<String, Object> getBookList(PageDto pageDto) {
        //获取图书
        List<Book> bookList = bookDao.getBookList(pageDto);
        //获取图书总数量
        long bookAccount = bookDao.getAllBookCount();
        Map<String, Object> bookMap = new HashMap<>(16);
        bookMap.put("bookList", bookList);
        bookMap.put("bookAccount", bookAccount);
        return bookMap;
    }

    @Override
    public Map<String, Object> getBookListRoot(PageDto pageDto) {
        //获取图书
        List<Book> bookList = bookDao.getBookListRoot(pageDto);
        //获取图书总数量
        long bookAccount = bookDao.getAllBookCountRoot();
        Map<String, Object> bookMap = new HashMap<>(16);
        bookMap.put("bookList", bookList);
        bookMap.put("bookAccount", bookAccount);
        return bookMap;
    }

    @Override
    public Book getBookByName(String bookName) {
        return bookDao.getBookByName(bookName);
    }

    @Override
    public Map<String, Object> getBookListByNameOrByClasses(String nameOrClasses, PageDto pageDto) {
        //获取图书数量
        long bookAccount = bookDao.getBookListByNameOrByClassesCount(nameOrClasses);
        //获取符合的图书
        List<Book> bookList = bookDao.getBookListByNameOrByClasses(nameOrClasses, pageDto);
        Map<String, Object> bookMap = new HashMap<>(16);
        bookMap.put("bookList", bookList);
        bookMap.put("bookAccount", bookAccount);
        return bookMap;
    }

    @Override
    public Boolean alterBookState(int bid, int state) {
        int result = bookDao.alterBookState(bid, state);
        return result == 1;
    }

    @Override
    public Boolean alterBookInfo(Book book, int bid) {
        int result = bookDao.alterBookInfo(book, bid);
        return result == 1;
    }

    @Override
    public Boolean addNewBook(Book book) {
        int result = bookDao.addNewBook(book);
        return result > 0;
    }

    @Override
    public Book getBookById(int id) {
        return bookDao.getBookById(id);
    }

    @Override
    public boolean afterBuy(Book book, int saleNum) {
        //获取图书
        Book nowBook = bookDao.getBookById(book.getBid());
        int nowNum = nowBook.getBrepertory() - saleNum;
        nowBook.setBrepertory(nowNum);
        int i = bookDao.alterBookInfo(nowBook, book.getBid());
        return i > 0;
    }
}
