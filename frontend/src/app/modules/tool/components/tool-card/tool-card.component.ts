import {Component, EventEmitter, Input, Output} from '@angular/core';
import {ToolResponseDto} from "../../../../services/models/tool-response-dto";

@Component({
  selector: 'app-tool-card',
  templateUrl: './tool-card.component.html',
  styleUrl: './tool-card.component.scss'
})
export class ToolCardComponent {
  private _tool: ToolResponseDto = {};
  private _toolImage: string | undefined;
  private _manage:boolean = false;

  @Output() private share:EventEmitter<ToolResponseDto> = new EventEmitter<ToolResponseDto>();
  @Output() private edit:EventEmitter<ToolResponseDto> = new EventEmitter<ToolResponseDto>();
  @Output() private archive:EventEmitter<ToolResponseDto> = new EventEmitter<ToolResponseDto>();
  @Output() private borrow:EventEmitter<ToolResponseDto> = new EventEmitter<ToolResponseDto>();
  @Output() private waitList:EventEmitter<ToolResponseDto> = new EventEmitter<ToolResponseDto>();
  @Output() private showDetails:EventEmitter<ToolResponseDto> = new EventEmitter<ToolResponseDto>();

  get tool(): ToolResponseDto {
    return this._tool;
  }

  @Input()
  set tool(value: ToolResponseDto) {
    this._tool = value;
  }

  get toolImage(): string | undefined {
   if (this.tool.photo){
      this._toolImage = `data:image/jpeg;base64,${this.tool.photo}`;
   }else{
      this._toolImage = 'https://via.placeholder.com/1900x1200';
   }
   // return placeholder image
    return this._toolImage;
  }

  get manage(): boolean {
    return this._manage;
  }

  @Input()
  set manage(value: boolean) {
    this._manage = value;
  }

  onShowDetails() {
    this.showDetails.emit(this.tool);
  }

  onBorrow() {
    this.borrow.emit(this.tool);
  }

  onWaitList() {
    this.waitList.emit(this.tool);
  }

  onEdit() {
    this.edit.emit(this.tool);
  }

  onShare() {
    this.share.emit(this.tool);
  }

  onArchive() {
    this.archive.emit(this.tool);
  }
}
