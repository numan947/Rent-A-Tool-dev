import { NgModule } from '@angular/core';
import {CommonModule, NgOptimizedImage} from '@angular/common';

import { ToolRoutingModule } from './tool-routing.module';
import { MainComponent } from './pages/main/main.component';
import { MenuComponent } from './components/menu/menu.component';
import { ToolListComponent } from './pages/tool-list/tool-list.component';
import { ToolCardComponent } from './components/tool-card/tool-card.component';
import { RatingComponent } from './components/rating/rating.component';
import { MytoolsComponent } from './pages/mytools/mytools.component';
import { ManageToolsComponent } from './pages/manage-tools/manage-tools.component';
import {FormsModule} from "@angular/forms";
import { BorrowedToolListComponent } from './pages/borrowed-tool-list/borrowed-tool-list.component';
import { MyReturnedToolsComponent } from './pages/my-returned-tools/my-returned-tools.component';


@NgModule({
  declarations: [
    MainComponent,
    MenuComponent,
    ToolListComponent,
    ToolCardComponent,
    RatingComponent,
    MytoolsComponent,
    ManageToolsComponent,
    BorrowedToolListComponent,
    MyReturnedToolsComponent
  ],
  imports: [
    CommonModule,
    ToolRoutingModule,
    NgOptimizedImage,
    FormsModule
  ]
})
export class ToolModule { }
