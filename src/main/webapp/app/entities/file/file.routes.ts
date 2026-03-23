import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';

import FileResolve from './route/file-routing-resolve.service';

const fileRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/file').then(m => m.File),
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/file-detail').then(m => m.FileDetail),
    resolve: {
      file: FileResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/file-update').then(m => m.FileUpdate),
    resolve: {
      file: FileResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default fileRoute;
