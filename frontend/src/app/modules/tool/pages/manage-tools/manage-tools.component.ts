import {Component, OnInit} from '@angular/core';
import {ToolRequestDto} from "../../../../services/models/tool-request-dto";
import {ToolService} from "../../../../services/services/tool.service";
import {ActivatedRoute, Router} from "@angular/router";
@Component({
  selector: 'app-manage-tools',
  templateUrl: './manage-tools.component.html',
  styleUrl: './manage-tools.component.scss'
})
export class ManageToolsComponent implements OnInit {
  errMessage: Array<string> = [];
  selectedPicture: string | undefined;
  selectedToolPicture: any;
  toolRequest:ToolRequestDto = {
    name: '',
    description: '',
    manufacturer: '',
    shareable: false
  };

  constructor(
    private toolService: ToolService,
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) {
  }

  ngOnInit(): void {
    const toolId = this.activatedRoute.snapshot.params['toolId'];
    if (toolId) {
      this.toolService.getTool(
        {
          'tool-id': toolId
        }
      ).subscribe(
        {
          next: (tool) => {
            this.toolRequest = {
              id: tool.id as number,
              name: tool.name as string,
              description: tool.description as string,
              manufacturer: tool.manufacturer as string,
              shareable: tool.shareable as boolean
            }
            if (tool.photo){
              this.selectedPicture = 'data:image/jpeg;base64,' + tool.photo;
            }
          },
          error: (error) => {
            this.errMessage = error.error.validationErrors;
          }
        }
      )
    }
  }

  onFileSelected(event:any) {
    console.log(event);
    this.selectedToolPicture = event.target.files[0];
    console.log(this.selectedToolPicture);
    if (this.selectedToolPicture) {
      const reader:FileReader = new FileReader();
      reader.onload = () => {
        this.selectedPicture = reader.result as string;
      }
      reader.readAsDataURL(this.selectedToolPicture);
    }
  }

  createNewTool() {
    console.log(this.toolRequest);
    console.log(this.selectedToolPicture);
    this.toolService.createTool({
      body: this.toolRequest,
    }).subscribe(
      {
        next: (toolId) => {
          if (!this.selectedToolPicture) {
            this.router.navigate(['/tools/my-tools']);
            return;
          }
          this.toolService.uploadToolPhoto(
            {
              'tool-id': toolId,
              body:{
                file: this.selectedToolPicture
              }
            }
          ).subscribe(
            {
              next: () => {
                this.router.navigate(['/tools/my-tools']);
              },
              error: (error) => {
                this.errMessage = error.error.validationErrors;
              }
            }
          )
        },
        error: (error) => {
          this.errMessage = error.error.validationErrors;
        }
      }
    )
  }
}
