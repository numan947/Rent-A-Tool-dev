import {Component, OnInit} from '@angular/core';
import {PageResponseDtoToolResponseDto} from "../../../../services/models/page-response-dto-tool-response-dto";
import {ToolService} from "../../../../services/services/tool.service";
import {Router} from "@angular/router";
import {ToolResponseDto} from "../../../../services/models/tool-response-dto";
import {archiveTool} from "../../../../services/fn/tool/archive-tool";

@Component({
  selector: 'app-mytools',
  templateUrl: './mytools.component.html',
  styleUrl: './mytools.component.scss'
})
export class MytoolsComponent implements OnInit {
  page = 0
  size = 5
  toolResponse:PageResponseDtoToolResponseDto = {};


  constructor(
    private toolService: ToolService,
    private router: Router
  ) {
  }

  ngOnInit(): void {
    this.findAllTools()
  }

  private findAllTools() {
    this.toolService.findToolsByOwner(
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

  protected readonly archiveTool = archiveTool;

  archiveThisTool(tool: ToolResponseDto) {
    this.toolService.archiveTool(
      {
        'tool-id': tool.id as number
      }
    ).subscribe(
      {
        next: () => {
          tool.archived = !tool.archived;
        },
        error: error => {
          console.log(error)
        }
      }
    )
  }

  editThisTool(tool: ToolResponseDto) {
    this.router.navigate(['tools', 'manage', tool.id]);
  }

  shareThisTool(tool: ToolResponseDto) {
    console.log('shareThisTool', tool)
    this.toolService.updateToolShareableStatus(
      {
        'tool-id': tool.id as number
      }
    ).subscribe(
      {
        next: () => {
          tool.shareable = !tool.shareable;
        },
        error: error => {
          console.log(error)
        }
      }
    )
  }
}
