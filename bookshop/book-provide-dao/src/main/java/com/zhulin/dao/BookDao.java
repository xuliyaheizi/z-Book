package com.zhulin.dao;

import com.zhulin.dto.PageDto;
import com.zhulin.entities.Book;

import java.util.List;

/**
 * @Program:bookshop
 * @author：ZHULIN
 * @Description: @Create：2022/4/5
 */
public interface BookDao {

    /**
     * 前台获取所有书籍信息
     * @param pageDto
     * @return
     */
    List<Book> getBookList(PageDto pageDto);

    /**
     * 后台获取所有书籍信息
     * @param pageDto
     * @return
     */
    List<Book> getBookListRoot(PageDto pageDto);

    /**
     * 根据书本名称获取书本信息
     * @param bookName
     * @return
     */
    Book getBookByName(String bookName);

    /**
     * 根据书名或分类模糊查询书籍
     * @param nameOrClasses
     * @param pageDto
     * @return
     */
    List<Book> getBookListByNameOrByClasses(String nameOrClasses, PageDto pageDto);

    /**
     * 根据书籍编号修改图书状态
     * @param bid
     * @param state
     * @return
     */
    int alterBookState(int bid, int state);

    /**
     * 根据编号修改图书信息
     * @param book
     * @param bid
     * @return
     */
    int alterBookInfo(Book book, int bid);

    /**
     * 添加图书
     * @param book
     * @return
     */
    int addNewBook(Book book);

    /**
     * 根据图书编号查找图书
     * @param id
     * @return
     */
    Book getBookById(int id);

    /**
     * 获取图书所有数量
     * @return
     */
    Long getAllBookCount();

    /**
     * 后台获取图书所有数量
     * @return
     */
    Long getAllBookCountRoot();

    /**
     * 获取图书数量
     * @param nameOrClasses
     * @return
     */
    Long getBookListByNameOrByClassesCount(String nameOrClasses);

}
