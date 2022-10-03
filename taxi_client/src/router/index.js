import Vue from 'vue'
import VueRouter from 'vue-router'
import PassengerMenu from '../components/PassengerMenu.vue'
import demo1 from '../views/PassengerViews/demo1.vue'
import Login from "../views/Login";
import PassengerInfo from "@/views/PassengerViews/PassengerInfo";
import Register from "@/views/Register";
import DriverMenu from "@/components/DriverMenu";
import DriverInfo from "@/views/DriverViews/DriverInfo";
import DriverDemo from "@/views/DriverViews/DriverDemo"
import AdminMenu from "@/components/AdminMenu";
import AdminInfo from "@/views/AdminViews/AdminInfo";
import AdminDemo from "@/views/AdminViews/AdminDemo";
import CarManage from "@/views/DriverViews/CarManage";
import WorkRecord from "@/views/DriverViews/WorkRecord";
import OrderRecord from "@/views/DriverViews/OrderRecord";
import AllowWorkRecord from "@/views/AdminViews/AllowWorkRecord";
import SettleRecord from "@/views/AdminViews/SettleRecord";
import SettleOrder from "@/views/AdminViews/SettleOrder";
import MapContainer from "@/components/MapContainer";
import PassengerOrderRecord from "@/views/PassengerViews/PassengerOrderRecord";
import TakeATaxi from "@/views/PassengerViews/TakeATaxi";
import StartWork from "@/views/DriverViews/StartWork";
import passengerReverseOrder from "@/views/PassengerViews/passengerReverseOrder";
import driverReserveOrder from "@/views/DriverViews/driverReserveOrder";
import DataAnalysis from "@/views/AdminViews/DataAnalysis";

Vue.use(VueRouter)

const routes = [
  {
    path: '/passengerMenu',
    component: PassengerMenu,
    children: [
      {
        path: "/demo1",
        component: demo1,
      },
      {
        path: "/passengerInfo",
        component: PassengerInfo,
      },
      {
        path:"/passengerOrderRecord",
        component: PassengerOrderRecord,
      },
      {
        path: "/takeATaxi",
        name: "takeATaxi",
        component: TakeATaxi,
      },
      {
        path: "/passengerReverseOrder",
        component: passengerReverseOrder,
      },
    ],
  },
  {
    path: '/driverMenu',
    component: DriverMenu,
    children: [
      {
        path: "/driverDemo",
        component: DriverDemo,
      },
      {
        path: "/driverInfo",
        component: DriverInfo,
      },
      {
        path: "/carManage",
        component: CarManage,
      },
      {
        path: "/workRecord",
        component: WorkRecord,
      },
      {
        path: "/orderRecord",
        component: OrderRecord,
      },
      {
        path: "/startWork",
        name: "startWork",
        component: StartWork,
      },
      {
        path: "/driverReserveOrder",
        component: driverReserveOrder,
      },
    ],
  },
  {
    path: '/adminMenu',
    component: AdminMenu,
    children: [
      {
        path: "/adminDemo",
        component: AdminDemo,
      },
      {
        path: "/settleRecord",
        component: SettleRecord,
      },
      {
        path: "/allowWorkRecord",
        component: AllowWorkRecord,
      },
      {
        path: "/adminInfo",
        component: AdminInfo,
      },
      {
        path: "/settleOrder",
        component: SettleOrder,
      },
      {
        path: "/dataAnalysis",
        component: DataAnalysis,
      },
    ],
  },
  {
    path: "/",
    component: Login
  },
  {
    path: "/register",
    component: Register,
  },
  {
    path:"/mapContainer",
    component: MapContainer,
  },
]


const router = new VueRouter({
  routes
})

// router.beforeEach((to, from, next)=>{
//   console.log(to);
//   let token = localStorage.getItem("userName");
//   if(token || to.path === "/" || to.path === "/register") {
//     next();
//   }
//   else {
//     next("/");
//   }
// })

export default router
