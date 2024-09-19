import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {MainComponent} from "./pages/main/main.component";
import {ToolListComponent} from "./pages/tool-list/tool-list.component";
import {MytoolsComponent} from "./pages/mytools/mytools.component";
import {ManageToolsComponent} from "./pages/manage-tools/manage-tools.component";
import {BorrowedToolListComponent} from "./pages/borrowed-tool-list/borrowed-tool-list.component";
import {MyReturnedToolsComponent} from "./pages/my-returned-tools/my-returned-tools.component";
import {authGuard} from "../../services/guard/auth.guard";

const routes: Routes = [
  {
    path:'',
    component:MainComponent,
    children:[
      {
        path: '',
        component: ToolListComponent,
        canActivate:[authGuard]
      },
      {
        path:'my-tools',
        component: MytoolsComponent,
        canActivate:[authGuard]
      },
      {
        path: 'manage',
        component: ManageToolsComponent,
        canActivate:[authGuard]
      },
      {
        path: 'manage/:toolId',
        component: ManageToolsComponent,
        canActivate:[authGuard]
      },
      {
        path:'my-borrowed-tools',
        component: BorrowedToolListComponent,
        canActivate:[authGuard]
      },
      {
        path:'my-returned-tools',
        component: MyReturnedToolsComponent,
        canActivate:[authGuard]
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ToolRoutingModule { }
