<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>备忘录</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap-4.3.1-dist/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/static/jquery/3.4.1/jquery-3.4.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/bootstrap-4.3.1-dist/js/bootstrap.min.js"></script>

    <script type="text/javascript" src="${pageContext.request.contextPath}/static/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/bootstrap-datetimepicker-master/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
    <link href="${pageContext.request.contextPath}/static/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" rel="stylesheet">

    <script src="${pageContext.request.contextPath}/static/vue/vue.js"></script>
    <script src="${pageContext.request.contextPath}/static/axios/dist/axios.js"></script>
</head>
<body class="bg-light">

<div id="list">
    <nav class="navbar navbar-dark bg-dark text-light">
        <a class="navbar-brand">备忘录</a>
        <div class="form-inline">
            <input id="searchInput" class="form-control mr-sm-2" placeholder="请输入内容..." @keyup.enter="search()">
            <button class="btn btn-outline-success my-2 my-sm-0" @click="search()">搜素</button>
        </div>
    </nav>

    <div class="container" style="margin-top: 60px">
        <ul class="list-group shadow">
            <li class="list-group-item">
                <div class="row">
                    <div class="col-8"><h4 class="font-weight-light" style="margin: 4px">备忘录</h4></div>
                    <div class="col-4 text-right">
                        <button class="btn btn-outline-primary" style="margin-left: 6px" data-toggle="modal" data-target="#addOrUpdateModal" @click="openAddOrUpdateModal(0)">新建</button>
                    </div>
                </div>
            </li>
            <li class="list-group-item" v-for="row in rows">
                <div class="row">
                    <div class="col-10 text-wrap">
                        <div class="container">
                            <div class="row"><p>{{ row.content }}</p></div>
                            <div class="row"><small class="text-muted">{{ row.updateDate | formatDate }}</small></div>
                        </div>
                    </div>
                    <div class="col-2 text-right">
                        <button class="btn btn-outline-warning" style="margin-left: 6px" data-toggle="modal" data-target="#addOrUpdateModal" @click="openAddOrUpdateModal(row)">编辑</button>
                        <button class="btn btn-outline-danger" style="margin-left: 6px" type="submit" @click="del(row.id)">删除</button>
                    </div>
                </div>
            </li>
        </ul>
        <ul class="pagination" style="margin-top: 60px">
            <li class="page-item ml-auto">
                <button class="page-link" @click="pageChange(1)">首页</button>
            </li>
            <li class="page-item">
                <button class="page-link" aria-label="Previous" @click="pageChange(currentPage-1)">
                    <span aria-hidden="true">&laquo;</span>
                </button>
            </li>
            <li class="page-item disabled">
                <button class="page-link" href='#'>{{ currentPage }}/{{ totalPage }}</button>
            </li>
            <li class="page-item">
                <button class="page-link" aria-label="Next" @click="pageChange(currentPage+1)">
                    <span aria-hidden="true">&raquo;</span>
                </button>
            </li>
            <li class="page-item">
                <button class="page-link" @click="pageChange(totalPage)">尾页</button>
            </li>
        </ul>
    </div>

    <div class="modal fade" id="addOrUpdateModal">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <!-- 模态框头部 -->
                <div class="modal-header">
                    <h4 id="modalTitle" class="modal-title">新建/编辑</h4>
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                </div>
                <!-- 模态框主体 -->
                <div class="modal-body">
                    <form>
                        <label class="col-form-label">备忘内容：</label>
                        <textarea id="content" class="form-control"></textarea>
                        <label class="col-form-label">是否提醒：</label>
                        <div class="form-group input-group mb-3">
                            <div class="input-group-prepend">
                                <div class="input-group-text">
                                    <input id="isRemind" type="checkbox"/>
                                </div>
                            </div>
                            <input type="text" readonly name="remindDate" id="remindDate" class="form-control" placeholder="提醒时间"/>
                        </div>
                    </form>
                </div>
                <!-- 模态框底部 -->
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" data-dismiss="modal" @click="save()">保存</button>
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">关闭</button>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    $(function () {
        $('#remindDate').datetimepicker({
            format: 'yyyy-mm-dd hh:ii',
            autoclose: true,
            language: 'zh-CN',
            minuteStep: 1
        });
    });

    var list = new Vue({
        el: '#list',
        data () {
            return {
                rows: null,
                total: null,
                totalPage: null,
                currentPage: null,
                currentId: null,
                searchInput: null
            }
        },
        mounted () {
            this.pageChange(1);
        },
        filters: {
            formatDate(time) {
                var date = new Date(time);
                var year = date.getFullYear();
                var month = ("0" + (date.getMonth() + 1)).slice(-2);
                var day = ("0" + date.getDate()).slice(-2);
                var hours = ("0" + date.getHours()).slice(-2);
                var minutes = ("0" + date.getMinutes()).slice(-2);
                var ymdhm = year + "-" + month + "-" + day + " " + hours + ":" + minutes;
                return ymdhm;
            }
        },
        methods: {
            dateList: function(page) {
                axios.post('${pageContext.request.contextPath}/smpMemo/list.do', {
                    page:page,
                    rows:10,
                    content:this.searchInput
                })
                    .then(response => {
                        this.rows = response.data.rows;
                        this.total = response.data.total;
                        if(this.total % 10 == 0) this.totalPage = this.total / 10;
                        else this.totalPage = parseInt(this.total / 10) + 1;
                    })
                    .catch(function (error) {
                        console.log(error);
                    });
            },
            save: function() {
                var content = $("#content").val();
                var isRemind = $('#isRemind').is(":checked")?1:0;
                var remindDate = new Date($('#remindDate').val());
                axios.post('${pageContext.request.contextPath}/smpMemo/save.do', {
                    id:this.currentId,
                    content:content,
                    isRemind:isRemind,
                    remindDate:remindDate
                })
                    .then(response => {
                        if(response.data.success) {
                            this.pageChange(1);
                        }
                    })
                    .catch(function (error) {
                        console.log(error);
                    });
            },
            del: function (id) {
                axios.post('${pageContext.request.contextPath}/smpMemo/delete.do', {
                    id:id,
                })
                    .then(response => {
                        if(response.data.success) {
                            this.pageChange(1);
                        }
                    })
                    .catch(function (error) {
                        console.log(error);
                    });
            },
            yymmddhhmm: function(time) {
                var date = new Date(time);
                var year = date.getFullYear();
                var month = ("0" + (date.getMonth() + 1)).slice(-2);
                var day = ("0" + date.getDate()).slice(-2);
                var hours = ("0" + date.getHours()).slice(-2);
                var minutes = ("0" + date.getMinutes()).slice(-2);
                var ymdhm = year + "-" + month + "-" + day + " " + hours + ":" + minutes;
                return ymdhm;
            },
            pageChange: function(page) {
                if(page != 1) {
                    if(page < 1 || page > this.totalPage) return;
                }
                this.currentPage = page;
                this.dateList(this.currentPage);
            },
            openAddOrUpdateModal: function(row) {
                if(row == 0) {
                    this.currentId = null;
                    $("#modalTitle").text("新建");
                    $("#content").val("");
                    $('#isRemind').attr("checked", false);
                    $('#remindDate').val("");
                    return;
                }
                this.currentId = row.id;
                $("#modalTitle").text("编辑");
                $("#content").val(row.content);
                if(row.isRemind == 1) $('#isRemind').attr("checked", true);
                else $('#isRemind').attr("checked", false);
                if (row.remindDate != null && row.remindDate != 0) {
                    $('#remindDate').val(this.yymmddhhmm(row.remindDate));
                }
            },
            search: function() {
                this.searchInput = $("#searchInput").val();
                this.pageChange(1);
            }
        }
    })
</script>

</body>
</html>