package com.zhulin.service;

import com.zhulin.dto.PageDto;
import com.zhulin.entities.Book;

import java.util.Map;

/**
 * @Program:bookshop
 * @author：ZHULIN
 * @Description: @Create：2022/4/5
 */
public interface BookService {

    /**
     * 获取所有书籍信息
     * @param pageDto 分页信息
     * @return
     */
    Map<String, Object> getBookList(PageDto pageDto);

    /**
     * 后台获取所有书籍信息
     * @param pageDto
     * @return
     */
    Map<String, Object> getBookListRoot(PageDto pageDto);

    /**
     * 根据书本名称获取书本信息
     * @param bookName
     * @return
     */
    public Book getBookByName(String bookName);

    /**
     * 根据书名或分类模糊查询书籍
     * @param nameOrClasses
     * @param pageDto
     * @return
     */
    public Map<String, Object> getBookListByNameOrByClasses(String nameOrClasses, PageDto pageDto);

    /**
     * 根据图书编号修改图书状态
     * @param bid
     * @param state
     * @return
     */
    public Boolean alterBookState(int bid, int state);

    /**
     * 根据书名修改图书信息
     * @param book
     * @param bid
     * @return
     */
    public Boolean alterBookInfo(Book book, int bid);

    /**
     * 添加图书
     * @param book
     * @return
     */
    public Boolean addNewBook(Book book);

    /**
     * 根据图书编号查找图书
     * @param id
     * @return
     */
    public Book getBookById(int id);

    /**
     * 购买后，图书库存减少
     * @param book
     * @param saleNum
     * @return
     */
    public boolean afterBuy(Book book, int saleNum);
}
