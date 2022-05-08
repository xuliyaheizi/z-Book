package com.zhulin.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhulin.dao.BookDao;
import com.zhulin.dto.PageDto;
import com.zhulin.entities.Book;
import com.zhulin.mapper.BookMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author:ZHULIN
 * @Description:
 * @Date: 2022/4/5
 */
@Repository
public class BookDaoImpl implements BookDao {

    @Resource
    private BookMapper bookMapper;

    @Override
    public List<Book> getBookList(PageDto pageDto) {
        Page<Book> bookPage = new Page<>(pageDto.getPageIndex(), pageDto.getPageSize());
        QueryWrapper<Book> queryWrapper = new QueryWrapper<Book>();
        queryWrapper.eq("bstate", 0);
        Page<Book> page = bookMapper.selectPage(bookPage, queryWrapper);
        return page.getRecords();
    }

    @Override
    public List<Book> getBookListRoot(PageDto pageDto) {
        Page<Book> bookPage = new Page<>(pageDto.getPageIndex(), pageDto.getPageSize());
        QueryWrapper<Book> queryWrapper = new QueryWrapper<Book>();
        Page<Book> page = bookMapper.selectPage(bookPage, queryWrapper);
        return page.getRecords();
    }

    @Override
    public Book getBookByName(String bookName) {
        return bookMapper.selectOne(new QueryWrapper<Book>().eq("bname", bookName));
    }

    @Override
    public List<Book> getBookListByNameOrByClasses(String nameOrClasses, PageDto pageDto) {
        Page<Book> bookPage = new Page<>(pageDto.getPageIndex(), pageDto.getPageSize());
        QueryWrapper<Book> bookQueryWrapper = new QueryWrapper<>();
        bookQueryWrapper.like("bname", nameOrClasses).or().like("bclasses", nameOrClasses).and(i -> i.eq("bstate", 0));
        bookQueryWrapper.eq("bstate", 0);
        Page<Book> page = bookMapper.selectPage(bookPage, bookQueryWrapper);
        return page.getRecords();
    }

    @Override
    public int alterBookState(int bid, int state) {
        Book book = new Book();
        book.setBstate(state);
        UpdateWrapper<Book> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("bid", bid);
        return bookMapper.update(book, updateWrapper);
    }

    @Override
    public int alterBookInfo(Book book, int bid) {
        UpdateWrapper<Book> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("bid", bid);
        return bookMapper.update(book, updateWrapper);
    }

    @Override
    public int addNewBook(Book book) {
        return bookMapper.insert(book);
    }

    @Override
    public Book getBookById(int id) {
        return bookMapper.selectById(id);
    }

    @Override
    public Long getAllBookCount() {
        QueryWrapper<Book> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("bstate", 0);
        return bookMapper.selectCount(queryWrapper);
    }

    @Override
    public Long getAllBookCountRoot() {
        return bookMapper.selectCount(null);
    }

    @Override
    public Long getBookListByNameOrByClassesCount(String nameOrClasses) {
        QueryWrapper<Book> bookQueryWrapper = new QueryWrapper<>();
        bookQueryWrapper.like("bname", nameOrClasses).or().like("bclasses", nameOrClasses).and(i -> i.eq("bstate", 0));
        bookQueryWrapper.eq("bstate", 0);
        return bookMapper.selectCount(bookQueryWrapper);
    }

}
