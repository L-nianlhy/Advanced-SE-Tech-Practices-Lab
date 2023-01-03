<template>
    <div style="width: 1400px;margin: 0 auto">
        <div class="rank-box" style="width: 900px;height: auto;margin: 0 auto;border-radius: 10px">
            <div class="title-box" style="width: 100%;height: 140px;border-top-left-radius: 10px;border-top-right-radius: 10px;margin-bottom: 0">
                <img src="../assets/rank_title.png" style="width: 500px;height: 160px;">
            </div>
            <div style="width: 100%;height: 50px;margin-top: 10px;margin-bottom: 10px">
                <div style="width: 620px;height: 50px;border-radius: 20px;background: #f0fafd;border:2px solid #cceff5;box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);float: left;margin-left: 25px">
                    <div class="cols" style="margin-left: 20px">名次</div>
                    <div class="cols" style="margin-left: 200px">用户名</div>
                    <div class="cols" style="margin-left: 200px">评分</div>
                </div>
                <div v-show="this.role==='employee'" style="float: left;width: 250px;height: 50px;margin-left: 0;">
                    <span style="font-size: 20px;float: left;margin-top: 10px;margin-left: 15px">我的排名：</span>
                    <span style="font-size: 35px;float: left;margin-top: 0;margin-left: 20px;font-weight: bolder;color: coral">{{this.myRank}}</span>
                </div>
            </div>

            <div class="rank-content-box" style="width: 100%;min-height:850px;height:auto;background-color: white;border-bottom-left-radius: 10px;border-radius: 10px;border:2px solid #cceff5;margin-top: 0;box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);padding-top: 15px">

                <ul class="rank" style="list-style: none;padding-left: 0;margin-top: 0">
                    <li v-for="(item,i) in employees" class="rank-item">
                        <div style="width: 100%;height:80px;" :style="{'background-color':this.colorList[i%2]}">
                            <div style="float: left;width: 100px;height: 80%;margin-left: 20px;font-size: 40px;font-weight: bolder;margin-top: 15px;color: #7a7fb6;">{{i+1}}</div>
                            <div style="float: left;width: 215px;height: 60%;margin-left: 100px;white-space: nowrap; text-overflow: ellipsis; overflow:hidden;font-size: 20px;font-weight: bolder;margin-top: 25px">{{item.employeeName}}</div>
                            <div style="float: left;width: 100px;height: 80%;margin-left: 110px;font-size: 40px;font-weight: bolder;margin-top: 15px;color: #595d9b">{{item.score}}</div>
                            <div style="float: left;width: 100px;height: 60%;margin-left: 110px;margin-top: 30px" v-show="this.role==='employer'"><el-button @click="readEmployeeInfo(item.employeeId)" type="text" style="font-size: 20px">查看详情</el-button> </div>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</template>

<script>
    import { TARGET } from "@/api/_prefix";
    const axios = require("axios");
    export default {
        name: "Rank",
        data(){
            return{
                employees:[],
                uid:parseInt(window.localStorage.getItem("userId")),
                colorList:['#FFFFFF','#f0fafd'],
                role:window.localStorage.getItem('role'),
                myRank:'未上榜'
            }
        },
        methods:{
            readEmployeeInfo(eid){
                window.localStorage.setItem('authorId',eid);
                this.$router.push("/employer/employeeInfo");
            }
        },
        created:function () {
            axios.post(`${TARGET}/user/refreshRank`,).then(res=>{
            });
            axios.post(`${TARGET}/user/getRank`,).then(res=>{
                console.log(res);
                this.employees=res.data;
                for(var i=0;i<this.employees.length;i++){
                    this.employees[i].score = Number(this.employees[i].score).toFixed(1);
                }
            });
            axios.post(`${TARGET}/employee/getRankByEmployeeId?employeeId=${this.uid}`).then(res => {
                console.log(res);
                var x = res.data;
                if(x!=0){
                    this.myRank=x.toString();
                }
            });
        }
    };

</script>

<style scoped>
    .cols{
        float:left;
        font-size: 25px;
        font-weight: bolder;
        margin-top: 7px;
        color: #808080;
    }
</style>
