import { Routes } from '@angular/router';


export const routes: Routes = [
    {
        path: 'frontpage',
        loadChildren: () => import('./frontpage/frontpage.module').then(m => m.FrontpageModule)
    },
    {
        path: 'backend',
        loadChildren: () => import('./backend/backend.module').then(m => m.BackendModule)
    },
    {
        path: 'delivery',
        loadChildren: () => import('./delivery/delivery.module').then(m => m.DeliveryModule)
    },
    { path: '', redirectTo: '/frontpage', pathMatch: 'full' },
    { path: '**', redirectTo: '/frontpage' }
];