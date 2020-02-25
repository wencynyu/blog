package com.yuwenxin.blog.core;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageUtil<T> {

    /**
     * 必要的：当前页，总数，单页内容数(直接引用settings接口中的常数值)
     * 根据必要属性可以计算出：
     * 1.总页数 = 总数 / 单页内容数
     * 2.尾页 = 总页数
     * 3.上一页 = 当前页 - 1，下一页 = 当前页 + 1
     *
     * 预计效果：
     * 1.总页数 >= 10
     * 【首页 1 2 3 ... cur-1 cur cur+1 cur+2 ... last-2 last-1 last 尾页 上一页 下一页 []页】
     * 2.总页数 < 10
     * 【首页 1 2 3 4 cur 6 7 8 9 尾页 上一页 下一页 []页】
     */

    private int currentPage;
    private final int pageSize = Settings.DEFAULT_PAGENUM;
    private int tableCount;  // 数据库表中的总数

    private int pageCount;  //总页数
    private final int beginPageIndex = 1;
    private int endPageIndex;

    private int preIndex,nextIndex;

    private List<T> tableList;  // 查询的列表结果保存在PageUtil中
    private List<Integer> pages;

    public PageUtil(int currentPage, int tableCount){
        super();
        this.currentPage = currentPage;
        this.tableCount = tableCount;
        this.pageCount=tableCount / pageSize + 1;
        // firstIndex
        this.endPageIndex = pageCount;
        this.preIndex = currentPage==beginPageIndex?beginPageIndex:currentPage - 1;
        this.nextIndex = currentPage==endPageIndex?endPageIndex:currentPage+1;
        this.pages = buildPages();

    }

    private List<Integer> buildPages(){
        List<Integer> res = new ArrayList<>();

        if (pageCount<10){
            for (int i = 1; i < pageCount + 1; i++) {
                res.add(i);
            }
        }else {
            for (int i = 1; i < 11; i++) {
                if (i <= 3){
                    res.add(i);
                }else if (i >= 8){
                    res.add(i);
                }else {
                    res.add(i - 1, currentPage - 5 + i);
                }
            }
        }

        return res;
    }

}
