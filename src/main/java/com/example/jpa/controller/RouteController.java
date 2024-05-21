package com.example.jpa.controller;

import com.example.jpa.domain.Book;
import com.example.jpa.domain.Member;
import com.example.jpa.service.BookService;
import com.example.jpa.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class RouteController {

    private final BookService bookService;
    private final MemberService memberService;


    @GetMapping(path = "/ui/list")
    public String bookList(Model model) {

        List<Book> bookList = bookService.getList();
        model.addAttribute("bookList", bookList);

        return "book/list";
    }

    @GetMapping(path = "/member/register")
    public String register() {
        return "member/register";
    }

    @PostMapping(path = "/member/register")
    public String register(Member member) {
        memberService.register(member);

        return "redirect:/ui/list";
    }
}
