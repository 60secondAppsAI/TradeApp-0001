import { createRouter, createWebHashHistory } from 'vue-router';
import { useAuth } from '../stores/auth';
import SignIn from '../components/SignIn.vue';
import Settings from '../components/Settings.vue';
import MyDashboard from '../components/MyDashboard.vue';

// Dynamic imports for module components
import Trades from '../components/Trades.vue';
import TradeDetail from '../components/TradeDetail.vue';
import TradeSettlements from '../components/TradeSettlements.vue';
import TradeSettlementDetail from '../components/TradeSettlementDetail.vue';
import TradeFees from '../components/TradeFees.vue';
import TradeFeeDetail from '../components/TradeFeeDetail.vue';
import TradeAudits from '../components/TradeAudits.vue';
import TradeAuditDetail from '../components/TradeAuditDetail.vue';

const routes = [
  {
    path: '/',
    name: 'home',
    redirect: '/dashboard'
  },
  {
    path: '/dashboard',
    name: 'Dashboard',
    component: MyDashboard
  },
  
  // Dynamic module routes
  {
    path: '/trades',
    name: 'Trades',
    component: Trades
  },
  {
    path: '/trade/:id',
    name: 'TradeDetail',
    component: TradeDetail,
    props: true
  },
  {
    path: '/tradeSettlements',
    name: 'TradeSettlements',
    component: TradeSettlements
  },
  {
    path: '/tradeSettlement/:id',
    name: 'TradeSettlementDetail',
    component: TradeSettlementDetail,
    props: true
  },
  {
    path: '/tradeFees',
    name: 'TradeFees',
    component: TradeFees
  },
  {
    path: '/tradeFee/:id',
    name: 'TradeFeeDetail',
    component: TradeFeeDetail,
    props: true
  },
  {
    path: '/tradeAudits',
    name: 'TradeAudits',
    component: TradeAudits
  },
  {
    path: '/tradeAudit/:id',
    name: 'TradeAuditDetail',
    component: TradeAuditDetail,
    props: true
  },

  // Core application routes
  {
    path: '/signin',
    name: 'SignIn',
    component: SignIn
  },
//  {
//    path: '/users',
//    name: 'Users',
//    component: Users,
//    meta: { requiresAuth: true }
//  },
  {
    path: '/settings',
    name: 'Settings',
    component: Settings,
    meta: { requiresAuth: true }
  },
  {
    path: '/:pathMatch(.*)*',
    redirect: '/dashboard'
  }
];

const router = createRouter({
  history: createWebHashHistory(import.meta.env.BASE_URL),
  routes,
  scrollBehavior(to, from, savedPosition) {
    return savedPosition || { top: 0 };
  }
});

// Navigation guard for authentication
router.beforeEach((to, from, next) => {
  const auth = useAuth();
  
  if (to.meta.requiresAuth && !auth.isAuthenticated) {
    next({ name: 'SignIn', query: { redirect: to.fullPath } });
  } else if (to.name === 'SignIn' && auth.isAuthenticated) {
    next({ name: 'Dashboard' });
  } else {
    next();
  }
});

export default router;
