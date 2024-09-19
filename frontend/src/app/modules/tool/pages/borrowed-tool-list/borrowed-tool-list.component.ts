import {Component, OnInit} from '@angular/core';
import {PageResponseDtoToolTransactionDto} from "../../../../services/models/page-response-dto-tool-transaction-dto";
import {ToolService} from "../../../../services/services/tool.service";
import {Router} from "@angular/router";
import {ToolTransactionDto} from "../../../../services/models/tool-transaction-dto";
import {ToolReviewRequest} from "../../../../services/models/tool-review-request";
import {ReviewService} from "../../../../services/services/review.service";

@Component({
  selector: 'app-borrowed-tool-list',
  templateUrl: './borrowed-tool-list.component.html',
  styleUrl: './borrowed-tool-list.component.scss'
})
export class BorrowedToolListComponent implements OnInit{
  page = 0;
  size = 5;
  selectedTool: ToolTransactionDto | undefined = undefined;
  reviewRequestDto: ToolReviewRequest = {
    rating: 0,
    description: '',
    title: '',
    toolId: 0
  };

  constructor(
    private toolService: ToolService,
    private router: Router,
    private reviewService: ReviewService
  ) {
  }

  ngOnInit(): void {
    this.findAllBorrowedTools();
  }
  borrowedTools: PageResponseDtoToolTransactionDto = {};

  startReturnTool(tool:ToolTransactionDto) {
    this.selectedTool = tool;
  }

  private findAllBorrowedTools() {
    this.toolService.findAllBorrowedTools(
      {
        page: this.page,
        size: this.size
      }
    ).subscribe({
      next: (data) => {
        this.borrowedTools = data;
      },
      error: (error) => {
        console.log(error);
      }
    })
  }

  goToFirstPage() {
    this.page = 0;
    this.findAllBorrowedTools();
  }

  goToPreviousPage() {
    this.page = this.page - 1;
    this.findAllBorrowedTools();
  }

  goToPage(index: number) {
    this.page = index;
    this.findAllBorrowedTools();
  }

  goToNextPage() {
    this.page = this.page + 1;
    this.findAllBorrowedTools();
  }

  goToLastPage() {
    this.page = this.borrowedTools.totalPages as number - 1;
    this.findAllBorrowedTools();
  }

  get isLastPage(): boolean {
    return this.borrowedTools.last??false;

  }

  returnThisTool(withReview: boolean) {

    this.toolService.returnTool(
      {
        "tool-id": this.selectedTool?.id as number,
      }
    ).subscribe(
      {
        next: (data) => {
          if (withReview) {
            this.submitReview();
          }
          this.selectedTool = undefined;
          this.findAllBorrowedTools();
        },
        error: (error) => {

        }
      }
    )
  }

  private submitReview() {
    this.reviewRequestDto.toolId = this.selectedTool?.id as number;
    this.reviewService.saveReview(
      {
        body: this.reviewRequestDto
      }
    ).subscribe({
      next: (data) => {
        this.reviewRequestDto = {
          rating: 0,
          description: '',
          title: '',
          toolId: 0
        };
      },
      error: (error) => {
        console.log(error);
      }
    })
  }
}
