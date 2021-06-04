import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { JeuxService } from 'app/entities/jeux/jeux.service';
import { IJeux, Jeux } from 'app/shared/model/jeux.model';

describe('Service Tests', () => {
  describe('Jeux Service', () => {
    let injector: TestBed;
    let service: JeuxService;
    let httpMock: HttpTestingController;
    let elemDefault: IJeux;
    let expectedResult: IJeux | IJeux[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(JeuxService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Jeux(
        0,
        'AAAAAAA',
        currentDate,
        'AAAAAAA',
        0,
        0,
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dateCreation: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Jeux', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateCreation: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateCreation: currentDate,
          },
          returnedFromService
        );

        service.create(new Jeux()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Jeux', () => {
        const returnedFromService = Object.assign(
          {
            nom: 'BBBBBB',
            dateCreation: currentDate.format(DATE_FORMAT),
            concepteur: 'BBBBBB',
            prix: 1,
            meilleurScore: 1,
            lienJouer: 'BBBBBB',
            logo: 'BBBBBB',
            setupFile: 'BBBBBB',
            description: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateCreation: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Jeux', () => {
        const returnedFromService = Object.assign(
          {
            nom: 'BBBBBB',
            dateCreation: currentDate.format(DATE_FORMAT),
            concepteur: 'BBBBBB',
            prix: 1,
            meilleurScore: 1,
            lienJouer: 'BBBBBB',
            logo: 'BBBBBB',
            setupFile: 'BBBBBB',
            description: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateCreation: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Jeux', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
