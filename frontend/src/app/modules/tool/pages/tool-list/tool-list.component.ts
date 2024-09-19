import {Component, OnInit} from '@angular/core';
import {ToolService} from "../../../../services/services/tool.service";
import {Router} from "@angular/router";
import {PageResponseDtoToolResponseDto} from "../../../../services/models/page-response-dto-tool-response-dto";
import {ToolResponseDto} from "../../../../services/models/tool-response-dto";

@Component({
  selector: 'app-tool-list',
  templateUrl: './tool-list.component.html',
  styleUrl: './tool-list.component.scss'
})
export class ToolListComponent implements OnInit{
  page = 0
  size = 5
  toolResponse:PageResponseDtoToolResponseDto = {};
  public message: string = '';
  level: string = 'success';


  constructor(
    private toolService: ToolService,
    private router: Router
  ) {
  }

  ngOnInit(): void {
    this.findAllTools()
    }

  private findAllTools() {
    this.toolService.findAllTools(
      {
        page: this.page,
        size: this.size
      }
    ).subscribe(
      {
        next: tools => {
          this.toolResponse = tools;
        },
        error: error => {
          console.log(error)
        }
      }
    )
  }

  goToFirstPage() {
    this.page = 0;
    this.findAllTools();
  }

  goToPreviousPage() {
    this.page = this.page - 1;
    this.findAllTools();
  }

  goToPage(index: number) {
    this.page = index;
    this.findAllTools();
  }

  goToNextPage() {
    this.page = this.page + 1;
    this.findAllTools();
  }

  goToLastPage() {
    this.page = this.toolResponse.totalPages as number - 1;
    this.findAllTools();
  }

  get isLastPage(): boolean {
    return this.toolResponse.last??false;

  }

  borrowBook(tool: ToolResponseDto) {
    this.message = '';
    this.toolService.borrowTool({
      'tool-id': tool.id as number
    }).subscribe({
      next: () => {
        this.level = 'success';
        this.message = 'Tool successfully added to your borrowed tools';

        setTimeout(() => {
          this.message = '';
        }, 3000);
      },
      error: error => {
        this.level = 'danger';
        console.log(error)
        this.message = error.error.error;
        setTimeout(() => {
          this.message = '';
        }, 3000);
      }
    })
  }
}
