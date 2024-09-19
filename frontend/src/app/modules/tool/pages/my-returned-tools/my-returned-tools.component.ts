import {Component, OnInit} from '@angular/core';
import {ToolTransactionDto} from "../../../../services/models/tool-transaction-dto";
import {ToolReviewRequest} from "../../../../services/models/tool-review-request";
import {ToolService} from "../../../../services/services/tool.service";
import {Router} from "@angular/router";
import {PageResponseDtoToolTransactionDto} from "../../../../services/models/page-response-dto-tool-transaction-dto";

@Component({
  selector: 'app-my-returned-tools',
  templateUrl: './my-returned-tools.component.html',
  styleUrl: './my-returned-tools.component.scss'
})
export class MyReturnedToolsComponent implements OnInit {
  returnedTools: PageResponseDtoToolTransactionDto = {};
  page = 0;
  size = 5;
  message: string = '';
  level: string =   'success';

  constructor(
    private toolService: ToolService,
    private router: Router,
  ) {
  }

  ngOnInit(): void {
    this.findAllReturnedTools();
  }

  private findAllReturnedTools() {
    this.toolService.findAllReturnedTools(
      {
        page: this.page,
        size: this.size
      }
    ).subscribe({
      next: (data) => {
        this.returnedTools = data;
      },
      error: (error) => {
        console.log(error);
      }
    })
  }

  goToFirstPage() {
    this.page = 0;
    this.findAllReturnedTools();
  }

  goToPreviousPage() {
    this.page = this.page - 1;
    this.findAllReturnedTools();
  }

  goToPage(index: number) {
    this.page = index;
    this.findAllReturnedTools();
  }

  goToNextPage() {
    this.page = this.page + 1;
    this.findAllReturnedTools();
  }

  goToLastPage() {
    this.page = this.returnedTools.totalPages as number - 1;
    this.findAllReturnedTools();
  }

  get isLastPage(): boolean {
    return this.returnedTools.last??false;

  }

  approveThisReturn(tool: ToolTransactionDto) {
    if (!tool.returned){
      return;
    }
    this.toolService.approveReturnTool({
      "tool-id": tool.id as number
    }).subscribe({
      next: () => {
        this.findAllReturnedTools();
        this.message = 'Tool return approved successfully';
        this.level = 'success';
        setTimeout(() => {
          this.message = '';

        }, 3000);
      },
      error: (error) => {
        console.log(error);
        this.message = 'Tool return approval failed';
        this.level = 'danger';
        setTimeout(() => {
          this.message = '';
        }, 3000);

      }
    });
  }
}
