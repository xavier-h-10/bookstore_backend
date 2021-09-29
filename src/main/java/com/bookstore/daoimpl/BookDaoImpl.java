package com.bookstore.daoimpl;

import com.bookstore.dao.BookDao;
import com.bookstore.entity.Book;
import com.bookstore.repository.BookRepository;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookDaoImpl implements BookDao {

  BookRepository bookRepository;

  @Autowired
  void setBookRepository(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  @Override
  public List<Book> getBooks() {
    System.out.println("getBooks dao executed.");
    List<Book> bookList = bookRepository.getBooks();
    return bookList;
  }

  @Override
  public Book getBookByBookId(Integer bookId) {
    System.out.println("getBookByBookId dao executed.");
    return bookRepository.getBookByBookId(bookId);
  }

  @Override
  public void deleteBookByBookId(Integer bookId) {
    bookRepository.deleteBookByBookId(bookId);
  }

  @Override
  public void addBook(Map<String, String> params) {
    String name = params.get("name");
    String author = params.get("author");
    String isbn = params.get("isbn");
    String description = params.get("description");
    String image = params.get("image");
    String type = params.get("type");
    String brief = params.get("brief");
    if (name == null || author == null || isbn == null || description == null || image == null
        || type == null || brief == null) {
      return;
    }

    if (params.get("price") == null) {
      return;
    }
    BigDecimal price = new BigDecimal(params.get("price"));
    price = price.setScale(2, BigDecimal.ROUND_HALF_UP);  //保留两位，四舍五入

    if (params.get("inventory") == null) {
      return;
    }
    Integer inventory = new Integer(params.get("inventory"));

    bookRepository.addBook(name, author, price, isbn, inventory, description, image, type, brief);
  }

  @Override
  public List<Book> getBookByName(String name) {
    return bookRepository.getBookByName("%"+name+"%");
  }

  @Override
  public void updateBook(Map<String,String> params) {
    Integer bookId= Integer.valueOf(params.get("bookId"));
    if(bookId==null || bookId<=0) return;

    String name = params.get("name");
    if(name!=null) bookRepository.modifyName(bookId,name);

    String author = params.get("author");
    if(author!=null) bookRepository.modifyAuthor(bookId,author);

    String isbn = params.get("isbn");
    if(isbn!=null) bookRepository.modifyISBN(bookId,isbn);

    String description = params.get("description");
    if(description!=null) bookRepository.modifyDescription(bookId,description);

    String image = params.get("image");
    if(image!=null) bookRepository.modifyImage(bookId,image);

    String type = params.get("type");
    if(type!=null) bookRepository.modifyType(bookId,type);

    String brief = params.get("brief");
    if(brief!=null) bookRepository.modifyBrief(bookId,brief);

    BigDecimal price = new BigDecimal(params.get("price"));
    price = price.setScale(2, BigDecimal.ROUND_HALF_UP);  //保留两位，四舍五入
    if(price.compareTo(BigDecimal.ZERO)>0) bookRepository.modifyPrice(bookId,price);

    Integer inventory = new Integer(params.get("inventory"));
    if(inventory>0) bookRepository.modifyInventory(bookId,inventory);

  }

  @Override
  public PageInfo<Book> getBooksByPage(Integer num) {
    List<Book> books=bookRepository.getBooks();

        PageInfo<Book> pageInfo = PageHelper
        .startPage(num, 5)
        .doSelectPageInfo(() -> bookRepository.getBooks());
        
        List<Book> result=new ArrayList<>();
        int st=Math.max((num-1)*5,0);
        int en=Math.min(books.size()-1,num*5-1);
        for(int i=st;i<=en;i++)
        {
          result.add(books.get(i));
        }

        pageInfo.setList(result);
        pageInfo.setTotal(books.size());
    return pageInfo;

  }


}
